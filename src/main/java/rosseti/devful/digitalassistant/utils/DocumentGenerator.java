package rosseti.devful.digitalassistant.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import rosseti.devful.digitalassistant.model.entities.EfficiencySuggestion;
import rosseti.devful.digitalassistant.model.entities.SuggestionResourceEntity;
import rosseti.devful.digitalassistant.model.resources.SuggestionResource;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class DocumentGenerator {

    @Autowired
    ResourceLoader resourceLoader;

    public File generate(EfficiencySuggestion efficiencySuggestion, SuggestionResource suggestionResource) {
       // File file = new File("/home/document.pdf");
        File file = new File("D:\\document.pdf");
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("D:\\document.pdf"));
                document.open();
                document = fillTable(document, efficiencySuggestion, suggestionResource);
                return file;
            } catch (DocumentException e) {
                e.printStackTrace();
            }


           // FileOutputStream fos = new FileOutputStream("/home/document.pdf"); //"D:\\document.docx"
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    private Document fillTable(Document document, EfficiencySuggestion efficiencySuggestion, SuggestionResource suggestionResource) {
        //Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        BaseFont bf = null; //подключаем файл шрифта, который поддерживает кириллицу
        Font bfunderline = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:tahoma.ttf");
            System.out.println("REEEEEES" + resource.getFile().getAbsolutePath());
            bf = BaseFont.createFont(resource.getFile().getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            bfunderline = new Font(bf, 12,    Font.ITALIC|Font.UNDERLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(bf);
        try {
            Paragraph paragraph = new Paragraph("Заместителю генерального директора – ", font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph("Директору по развитию", font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph("В.О. Акуличеву", font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph("\n\n", font);
            document.add(paragraph);
            paragraph = new Paragraph("Заявление на рационализаторское предложение", new Font(bf, 15,Font.NORMAL));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("\n\n", font);
            document.add(paragraph);
            paragraph = new Paragraph("Номер " + efficiencySuggestion.getRegNumber(), font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph("Дата " + efficiencySuggestion.getRegNumber(), font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph(efficiencySuggestion.getName(), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("\n", font);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(2);
            Stream.of("Имя", "Должность")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle, font));
                        table.addCell(header);
                    });
            efficiencySuggestion
                    .getAuthors()
                    .stream()
                    .forEach(
                        author -> {
                            table.addCell(new Phrase(author, font));
                            int index =  efficiencySuggestion.getAuthors().indexOf(author);
                            table.addCell(new Phrase(efficiencySuggestion.getAuthorsPositions().get(index), font));
                        }
            );
            document.add(table);
            paragraph = new Paragraph("Категория предложения в части цифровой трансформации:", font);
            document.add(paragraph);
            paragraph = new Paragraph(efficiencySuggestion.getProposalCategory(), font);
            document.add(paragraph);
            paragraph = new Paragraph("ОПИСАНИЕ ПРЕДЛОЖЕНИЯ", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph("Описание действительного положения с указанием существующих недостатков:", bfunderline);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph(suggestionResource.getCurrent(), font);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph("Описание предлагаемого решения:", bfunderline);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph(suggestionResource.getSolution(), font);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph("Ожидаемый положительный эффект от использования (технический, организационный, управленческий или иной):", bfunderline);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph(suggestionResource.getEffect(), font);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph("\n", font);
            document.add(paragraph);
            paragraph = new Paragraph("Необходимые затраты на внедрение:", bfunderline);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph("\n", font);
            document.add(paragraph);
            PdfPTable table2 = new PdfPTable(3);
            Stream.of("№", "Статья затрат", "Сумма с НДС, руб")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle, font));
                        table2.addCell(header);
                    });
            suggestionResource
                    .getCost().stream().forEach(
                            element -> {
                                Map.Entry<String, String> entry = new ArrayList<>(element.entrySet()).get(0);
                                table2.addCell(new Phrase(String.valueOf(suggestionResource
                                        .getCost().indexOf(element) + 1), font));
                                table2.addCell(new Phrase(String.valueOf(entry.getKey()), font));
                                table2.addCell(new Phrase(String.valueOf(entry.getValue()), font));
                            }
            );
            document.add(table2);
            paragraph = new Paragraph("Требуемые сроки на внедрение:", bfunderline);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph("\n", font);
            document.add(paragraph);
            PdfPTable table3 = new PdfPTable(3);
            Stream.of("№", "Название этапа", "Срок, дней")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle, font));
                        table3.addCell(header);
                    });
            suggestionResource
                    .getStages().stream().forEach(
                    element -> {
                        Map.Entry<String, String> entry = new ArrayList<>(element.entrySet()).get(0);
                        table3.addCell(new Phrase(String.valueOf(suggestionResource
                                .getCost().indexOf(element) + 1), font));
                        table3.addCell(new Phrase(String.valueOf(entry.getKey()), font));
                        table3.addCell(new Phrase(String.valueOf(entry.getValue()), font));
                    }
            );
            document.add(table3);
            document.newPage();
            paragraph = new Paragraph("Соглашение о вознаграждении:", bfunderline);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph("\n", font);
            document.add(paragraph);
            PdfPTable table4 = new PdfPTable(5);
            Stream.of("№", "Ф. И. О. автора (ов)", "% вознаграждения", "Подпись", "Дата")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle, font));
                        table4.addCell(header);
                    });
            suggestionResource
                    .getBounty().stream().forEach(
                    element -> {
                        Map.Entry<String, String> entry = new ArrayList<>(element.entrySet()).get(0);
                        table4.addCell(new Phrase(String.valueOf(suggestionResource
                                .getCost().indexOf(element) + 1), font));
                        table4.addCell(new Phrase(String.valueOf(entry.getKey()), font));
                        table4.addCell(new Phrase(String.valueOf(entry.getValue()), font));
                        table4.completeRow();
                    }
            );
            document.add(table4);
            paragraph = new Paragraph("\n", font);
            document.add(paragraph);
            paragraph = new Paragraph("Автор (ы)", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph = new Paragraph("\n\n", font);
            document.add(paragraph);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(30);
            lineSeparator.setAlignment(Element.ALIGN_RIGHT);
            document.add(lineSeparator);
            paragraph = new Paragraph("ФИО, Подпись", font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            paragraph = new Paragraph("\n\n", font);
            document.add(paragraph);
            LineSeparator lineSeparator2 = new LineSeparator();
            lineSeparator2.setPercentage(20);
            lineSeparator2.setAlignment(Element.ALIGN_RIGHT);
            document.add(lineSeparator2);
            paragraph = new Paragraph("Дата", font);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return document;
    }
}
