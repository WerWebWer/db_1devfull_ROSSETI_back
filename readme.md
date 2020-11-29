
# Backend клиент-серверного приложения для проекта РОССЕТИ

## Решение от команды 1DevFull


* Развертывание на Docker (мин. треб: 1 core, 2 ГБ)
* Реализован на Java (JDK11) / Python3.6

Цель backend: хранение данных и обработка запросов от frontend, который находится [ТУТ](https://github.com/kirillnepomiluev/rosseti).

---
Для запуска ввести команду `docker-compose up`. Для подключения - внешний `ip-addres` и `8080` порт.

---

Примеры команд:
1. Добавление новой записи
2. Получение записей из базы данных
3. Вызов семантического поиска
4. Генерация документов напечать по данным отправленным от клиента

---

Примеры запросов:
* GET `ip:port/api/getsuggestion/` - получение записей
* POST `ip:port/api/savesuggestion/` JSON - отправка новой записи
* GET `ip:port/api/getfile/{id}` - получение сгенерированного PDF файла сформированного по данным клиента {id}

---
Семантический поиск организован среди описаний записей, SemanticSearch.java запускает поиск. На выход получаем `[id,id] - value ` - вероятность между двумя записями (чем меньше чило - вероятность больше, чем больше число - вероятность меньше)

---

### Что тут есть

[Семантический поиск](https://github.com/WerWebWer/db_1devfull_ROSSETI_back#%D1%81%D0%B5%D0%BC%D0%B0%D0%BD%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B9-%D0%BF%D0%BE%D0%B8%D1%81%D0%BA)

[Telegram BOT](https://github.com/WerWebWer/db_1devfull_ROSSETI_back#telegram-bot)

[Пример семантического поиска](https://github.com/WerWebWer/db_1devfull_ROSSETI_back#%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80-%D1%81%D0%B5%D0%BC%D0%B0%D0%BD%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%BE%D0%B3%D0%BE-%D0%BF%D0%BE%D0%B8%D1%81%D0%BA%D0%B0)

### Семантический поиск

![](https://github.com/WerWebWer/db_1devfull_ROSSETI_back/blob/master/images/1.png)

Наш семантический поиск на Python - [WerWebWer/db_1devfull_ROSSETI_semantic_search](https://github.com/WerWebWer/db_1devfull_ROSSETI_semantic_search).

Недавно Google объявил, что он переходит от поиска по ключевым словам к полностью семантическому поиску. Не знаю, насколько круты алгоритмы поиска у мировых гигантов, но поиск в маленькой песочнице получается довольно семантическим. Конечно, с поиском по более менее крупным объёмам данных уже не всё так радужно, готовить слова надо очень тщательно, но тем не менее.

Сразу оговорюсь, вся разобраннная теория очень хорошо описана в статье на хабре - [ВОТ ТУТ](http://habrahabr.ru/post/110078/). Именно она послужила моей реализации.

Итак, у нас есть список с десяткоми документов, по которым мы и будем искать. Все документы по очередно вписываются в файл _data.txt_, который потом принимает и обрабатывает наш код.

Собственно есть входные данные, теперь нам нужно сделать три подготовительные операции:
1) Удалить разные запятые, точки, двоеточия, если есть html и прочий мусор из текста.
2) Привести все в нижний регистр и удалить все предлоги в, на, за, и тд.
3) Привести слова в нормальную форму, то есть поскольку для поиска слова типа премия, премий и прочее будет разными словами, надо это исправить.
4) Если мы просто хотим найти похожие документы, то можно удалить встречающиеся всего лишь один раз слова — для анализа сходства они бесполезны и будучи удалёнными позволят существенно сэкономить память.

Теперь сам алгоритм, благодаря математическим библиотекам питона тут все довольно просто.
5) Мы составляем матрицу нолей и единиц, соответственно представляющих отсутствие или наличие слова в документе.
6) Выполняем сингулярное разложение этой матрицы, в результате чего получаем три другие матрицы, в которых получим координаты документов и слов в пространстве.

На последнем этапе в упрощённом виде нам остается просто сравнить между собой координаты документов и/или слов: те, которые находятся ближе всего друг к другу и есть нужный результат, те которые подальше соответственно менее релевантны.

Все манипуляции с матрицами мы будем осуществлять с помощью __numpy__ и __scipy__ приводить слова к изначальной форме будем с помощью __nltk__. Поэтому устанавливаем…

``` javascript
1 pip install numpy
2 pip install nltk
3 pip install scipy
```

### Telegram BOT

Телеграм бот на Python - [WerWebWer/db_1devfull_ROSSETI_bot](https://github.com/WerWebWer/db_1devfull_ROSSETI_bot).

Его можно найти в Telegram по @rosseti_ri_bot

Решение - чат бот, позволяющий пользователю, ответив на вопросы сформировать идею, а так же помочь с офромлением документов.  

Чтобы программа на Python умела управлять Телеграм-ботами, нужно в самое начало кода добавить строки:

`import telebot; bot = telebot.TeleBot('токен');`

Единственное, о чём нужно не забыть — заменить слово «токен» на настоящий токен, который дал нам `@BotFather`. Открываем программу и добавляем.

Теперь научим бота реагировать на слово «Привет». Для этого добавим после строчек с импортом новый метод и сразу пропишем в нём реакцию на нужное слово. Так или инаяе прописываем сценарий ключевых слов - главных.

И последнее, что нам осталось сделать до запуска, — добавить после метода такую строчку:

`bot.polling(none_stop=True, interval=0)`

Она скажет программе, чтобы она непрерывно спрашивала у бота, не пришли ли ему какие-то новые сообщения. Запускаем программу и проверяем, как работает наш бот.

После добавляем начинку по кнопкам и в принципе бот готов.
`bot.send_message(message.chat.id, text, reply_markup)`

Не забываем про обработчик кнопок (отловка ивентов) - Указывая id кнопок.
```python
@bot.callback_query_handler(func=lambda call: True)
def callback_inline(call):
    if call.message:
        if call.data == "test":
            bot.edit_message_text(chat_id, message_id, text)
```

### Пример семантического поиска


##### Исходные документы

Ном.док-- 0 Текст-Human machine interface for ABC computer applications
Ном.док-- 1 Текст-A survey of user opinion of computer system response time
Ном.док-- 2 Текст-The EPS user interface management system
Ном.док-- 3 Текст-System and human system engineering testing of EPS
Ном.док-- 4 Текст-Relation of user perceived response time to error measurement
Ном.док-- 5 Текст-The generation of random, binary, ordered trees
Ном.док-- 6 Текст-The intersection graph of paths in trees
Ном.док-- 7 Текст-Graph minors IV: Widths of trees and well-quasi- ordering
Ном.док-- 8 Текст-Graph minors: A survey

##### Слова, которые встречаться только один раз:

    ['opinion', 'binari', 'in', 'engin', 'iv', 'width', 'order', 'test', 'error', 'intersect', 'abc', 'perceiv', 'path', 'generat', 'relat', 'to', 'measur', 'machin', 'manag', 'for', 'random', 'applic']
    
##### Стоп-слова:

    ['i', 'me', 'my', 'myself', 'we', 'our', 'ours', 'ourselves', 'you', 'your', 'yours', 'yourself', 'yourselves', 'he', 'him', 'his', 'himself', 'she', 'her', 'hers', 'herself', 'it', 'its', 'itself', 'they', 'them', 'their', 'theirs', 'themselves', 'what', 'which', 'who', 'whom', 'this', 'that', 'these', 'those', 'am', 'is', 'are', 'was', 'were', 'be', 'been', 'being', 'have', 'has', 'had', 'having', 'do', 'does', 'did', 'doing', 'a', 'an', 'the', 'and', 'but', 'if', 'or', 'because', 'as', 'until', 'while', 'of', 'at', 'by', 'for', 'with', 'about', 'against', 'between', 'into', 'through', 'during', 'before', 'after', 'above', 'below', 'to', 'from', 'up', 'down', 'in', 'out', 'on', 'off', 'over', 'under', 'again', 'further', 'then', 'once', 'here', 'there', 'when', 'where', 'why', 'how', 'all', 'any', 'both', 'each', 'few', 'more', 'most', 'other', 'some', 'such', 'no', 'nor', 'not', 'only', 'own', 'same', 'so', 'than', 'too', 'very', 's', 't', 'can', 'will', 'just', 'don', 'should', 'now']

##### Cлова(основа):

    ['human', 'interfac', 'comput', 'survey', 'user', 'system', 'respons', 'time', 'ep', 'tree', 'graph', 'minor']
    
##### Распределение слов по документам:

    {'interfac': [0, 2], 'user': [1, 2, 4], 'minor': [7, 8], 'comput': [0, 1], 'graph': [6, 7, 8], 'tree': [5, 6, 7], 'time': [1, 4], 'human': [0, 3], 'ep': [2, 3], 'system': [1, 2, 3, 3], 'survey': [1, 8], 'respons': [1, 4]}


##### Первая матрица заполненных строк и столбцов

```
[[ 1. 1. 0. 0. 0. 0. 0. 0. 0.]
[ 0. 0. 1. 1. 0. 0. 0. 0. 0.]
[ 0. 0. 0. 0. 0. 0. 1. 1. 1.]
[ 1. 0. 0. 1. 0. 0. 0. 0. 0.]
[ 1. 0. 1. 0. 0. 0. 0. 0. 0.]
[ 0. 0. 0. 0. 0. 0. 0. 1. 1.]
[ 0. 1. 0. 0. 1. 0. 0. 0. 0.]
[ 0. 1. 0. 0. 0. 0. 0. 0. 1.]
[ 0. 1. 1. 2. 0. 0. 0. 0. 0.]
[ 0. 1. 0. 0. 1. 0. 0. 0. 0.]
[ 0. 0. 0. 0. 0. 1. 1. 1. 0.]
[ 0. 1. 1. 0. 1. 0. 0. 0. 0.]]
```

##### Исходная частотная матрица число слов---12 больше либо равно числу документо-9

```
comput {[ 1. 1. 0. 0. 0. 0. 0. 0. 0.]}
ep {[ 0. 0. 1. 1. 0. 0. 0. 0. 0.]}
graph {[ 0. 0. 0. 0. 0. 0. 1. 1. 1.]}
human {[ 1. 0. 0. 1. 0. 0. 0. 0. 0.]}
interfac {[ 1. 0. 1. 0. 0. 0. 0. 0. 0.]}
minor {[ 0. 0. 0. 0. 0. 0. 0. 1. 1.]}
respons {[ 0. 1. 0. 0. 1. 0. 0. 0. 0.]}
survey {[ 0. 1. 0. 0. 0. 0. 0. 0. 1.]}
system {[ 0. 1. 1. 2. 0. 0. 0. 0. 0.]}
time {[ 0. 1. 0. 0. 1. 0. 0. 0. 0.]}
tree {[ 0. 0. 0. 0. 0. 1. 1. 1. 0.]}
user {[ 0. 1. 1. 0. 1. 0. 0. 0. 0.]}
```

##### Нормализованная по методу TF-IDF матрица: строк- слов -12 столбцов — документов--9.

```
comput {[ 0.5 0.25 0. 0. 0. 0. 0. 0. 0. ]}
ep {[ 0. 0. 0.38 0.38 0. 0. 0. 0. 0. ]}
graph {[ 0. 0. 0. 0. 0. 0. 0.55 0.37 0.37]}
human {[ 0.5 0. 0. 0.38 0. 0. 0. 0. 0. ]}
interfac {[ 0.5 0. 0.38 0. 0. 0. 0. 0. 0. ]}
minor {[ 0. 0. 0. 0. 0. 0. 0. 0.5 0.5]}
respons {[ 0. 0.25 0. 0. 0.5 0. 0. 0. 0. ]}
survey {[ 0. 0.25 0. 0. 0. 0. 0. 0. 0.5 ]}
system {[ 0. 0.18 0.27 0.55 0. 0. 0. 0. 0. ]}
time {[ 0. 0.25 0. 0. 0.5 0. 0. 0. 0. ]}
tree {[ 0. 0. 0. 0. 0. 1.1 0.55 0.37 0. ]}
user {[ 0. 0.18 0.27 0. 0.37 0. 0. 0. 0. ]}
```

##### Первые 2 столбца ортогональной матрицы U слов, сингулярного преобразования нормализованной матрицы: строки слов -12

```
comput {[-0.0045 0.3471]}
ep {[-0.0014 0.3404]}
graph {[-0.3777 0.0363]}
human {[-0.0017 0.4486]}
interfac {[-0.0017 0.4309]}
minor {[-0.2351 0.0542]}
respons {[-0.0053 0.218 ]}
survey {[-0.0827 0.1223]}
system {[-0.0039 0.4297]}
time {[-0.0053 0.218 ]}
tree {[-0.8917 -0.0508]}
user {[-0.0043 0.2744]}
```

Координаты x --0.891700 и y--0.050800 опорного слова --tree, от которого отсчитываются все расстояния.

##### Первые 2 строки диагональной матрица S

```
[[ 1.37269958 0. 0]
[ 0. 1.06667558]]
```

##### Первые 2 строки ортогональной матрицы Vt документов сингулярного преобразования нормализованной матрицы: столбцы документов -9.

```
[[ 0.0029 0.0189 0.0025 0.0024 0.005 0.7145 0.5086 0.4278 0.2175]
[-0.5749 -0.331 -0.453 -0.5026 -0.2996 0.0524 0.0075 -0.0204 -0.0953]]
```

##### Матрица для выявления скрытых связей

```
comput {[ 0.21 0.12 0.17 0.19 0.11 -0.01 0. 0.01 0.04]}
eps {[ 0.21 0.12 0.16 0.18 0.11 -0.02 0. 0.01 0.04]}
graph {[ 0.02 0.02 0.02 0.02 0.01 0.37 0.26 0.22 0.12]}
human {[ 0.28 0.16 0.22 0.24 0.14 -0.02 0. 0.01 0.05]}
interfac {[ 0.26 0.15 0.21 0.23 0.14 -0.02 0. 0.01 0.04]}
minor {[ 0.03 0.03 0.03 0.03 0.02 0.23 0.16 0.14 0.08]}
respons {[ 0.13 0.08 0.11 0.12 0.07 -0.01 0. 0.01 0.02]}
survey {[ 0.08 0.05 0.06 0.07 0.04 0.07 0.06 0.05 0.04]}
system {[ 0.26 0.15 0.21 0.23 0.14 -0.02 0. 0.01 0.04]}
time {[ 0.13 0.08 0.11 0.12 0.07 -0.01 0. 0.01 0.02]}
tree {[-0.03 0.01 -0.02 -0.02 -0.01 0.88 0.62 0.52 0.26]}
user {[ 0.17 0.1 0.13 0.15 0.09 -0.01 0. 0.01 0.03]}
```

##### Матрица косинусных расстояний между документами

```
[[ 1. 0.998649 1. 1. 0.999932 -0.06811 -0.009701
0.05267 0.405942]
[ 0.998649 1. 0.998673 0.998635 0.999186 -0.016168 0.04228
0.104496 0.452889]
[ 1. 0.998673 1. 1. 0.999938 -0.067637 -0.009226
0.053143 0.406376]
[ 1. 0.998635 1. 1. 0.999929 -0.068378 -0.00997
0.052401 0.405696]
[ 0.999932 0.999186 0.999938 0.999929 1. -0.056489 0.001942
0.064293 0.416555]
[-0.06811 -0.016168 -0.067637 -0.068378 -0.056489 1. 0.998292
0.992706 0.884128]
[-0.009701 0.04228 -0.009226 -0.00997 0.001942 0.998292 1. 0.998054
0.909918]
[ 0.05267 0.104496 0.053143 0.052401 0.064293 0.992706 0.998054 1.
0.934011]
[ 0.405942 0.452889 0.406376 0.405696 0.416555 0.884128 0.909918
0.934011 1. ]]
```

Кластеризация косинусных расстояний между документами.

##### Метки кластеров:

```
[1 1 1 1 1 0 0 0 0]
```

##### Координаты центроидов кластеров:

```
[[ 0.09520025 0.14587425 0.095664 0.09493725 0.10657525 0.9687815
0.976566 0.98119275 0.93201425]
[ 0.9997162 0.9990286 0.9997222 0.9997128 0.999797 -0.0553564
0.003065 0.0654006 0.4174916 ]]
```

##### Матрица косинусных расстояний между словами

```
comput {[ 1. 0.999961 0.108563 0.999958 0.999959 0.237261 0.999936
0.835577 0.999992 0.999936 -0.04393 0.999996]}
ep {[ 0.999961 1. 0.09976 1. 1. 0.228653 0.999796
0.830682 0.999988 0.999796 -0.052771 0.999933]}
graph {[ 0.108563 0.09976 1. 0.099439 0.099594 0.991462 0.119832
0.636839 0.104697 0.119832 0.988361 0.111252]}
human {[ 0.999958 1. 0.099439 1. 1. 0.228339 0.99979
0.830502 0.999986 0.99979 -0.053094 0.999929]}
interfac {[ 0.999959 1. 0.099594 1. 1. 0.22849 0.999793
0.830589 0.999987 0.999793 -0.052938 0.999931]}
minor {[ 0.237261 0.228653 0.991462 0.228339 0.22849 1. 0.248265
0.731936 0.233482 0.248265 0.960085 0.239888]}
respons {[ 0.999936 0.999796 0.119832 0.99979 0.999793 0.248265 1. 0.841755
0.999884 1. -0.032595 0.999963]}
survey {[ 0.835577 0.830682 0.636839 0.830502 0.830589 0.731936 0.841755 1.
0.833435 0.841755 0.512136 0.83706 ]}
system {[ 0.999992 0.999988 0.104697 0.999986 0.999987 0.233482 0.999884
0.833435 1. 0.999884 -0.047814 0.999978]}
time {[ 0.999936 0.999796 0.119832 0.99979 0.999793 0.248265 1. 0.841755
0.999884 1. -0.032595 0.999963]}
tree {[-0.04393 -0.052771 0.988361 -0.053094 -0.052938 0.960085 -0.032595
0.512136 -0.047814 -0.032595 1. -0.041227]}
user {[ 0.999996 0.999933 0.111252 0.999929 0.999931 0.239888 0.999963
0.83706 0.999978 0.999963 -0.041227 1. ]}
```

Кластеризация косинусных расстояний между словами

##### Метки кластеров

```
[1 1 0 1 1 0 1 1 1 1 0 1]
```

##### Координаты центроидов кластеров:

```
[[ 0.10063133 0.09188067 0.99327433 0.09156133 0.09171533 0.983849
0.111834 0.62697033 0.09678833 0.111834 0.98281533 0.10330433]
[ 0.98170167 0.98112844 0.16664533 0.98110611 0.98111689 0.29161989
0.98232411 0.85348389 0.98145933 0.98232411 0.01724133 0.98186144]]
```

##### Результаты анализа: Всего документов:9. Осталось документов после исключения не связанных: 9

```
№№ Док [0, 3]- 0.0-Косинусная мера расстояния -Общие слова -['human']
№№ Док [3, 2]- 0.0-Косинусная мера расстояния -Общие слова -['system', 'ep']
№№ Док [2, 4]- 0.0-Косинусная мера расстояния -Общие слова -['user']
№№ Док [4, 1]- 0.001-Косинусная мера расстояния -Общие слова -['user', 'respons', 'time']
№№ Док [6, 5]- 0.001-Косинусная мера расстояния -Общие слова -['tree']
№№ Док [7, 6]- 0.002-Косинусная мера расстояния -Общие слова -['graph', 'tree']
№№ Док [8, 7]- 0.067-Косинусная мера расстояния -Общие слова -['graph', 'minor']
№№ Док [1, 8]- 0.548-Косинусная мера расстояния -Общие слова -['survey']
```

##### Расположение в семантическом пространстве слов и документов


![](https://github.com/WerWebWer/db_1devfull_ROSSETI_back/blob/master/images/2.JPG)


![](https://github.com/WerWebWer/db_1devfull_ROSSETI_back/blob/master/images/3.JPG)

