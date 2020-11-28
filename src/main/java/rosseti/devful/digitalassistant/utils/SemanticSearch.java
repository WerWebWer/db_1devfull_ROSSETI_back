package rosseti.devful.digitalassistant.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SemanticSearch{ // Start Semantic search
	public static void findByString(String arg) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder("C:/Users/Werwe/AppData/Local/Programs/Python/Python36/python.exe", "c:/Users/Werwe/Desktop/db_1devfull_ROSSTI_back/src/main/java/rosseti/devful/digitalassistant/python/start.py", arg); // TODO: Инструкция ниже
		processBuilder.redirectErrorStream(true);
	 
		Process process = processBuilder.start();
		
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        }
	}
}
/*

1. Установить нужный питон, рекомендую Python 3.6
2. Установить нужные библиотеки:
	tkinter
	numpysklearn
	nltk
	sklearn
	// мб какие еще
3. Правильный путь

*/
