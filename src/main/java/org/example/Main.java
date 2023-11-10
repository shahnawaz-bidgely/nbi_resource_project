package org.example;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            final Set<String> stInsight = new HashSet<>();
            final Set<String> stNBI = new HashSet<>();
            final File file = new File("/Users/shahnawazshaikh/Desktop/NWN/seasonal/reource_sheet_script_6/NWN_NBI_resource_seasonal_final_6.xlsx");
            final File SQL = new File("/Users/shahnawazshaikh/Desktop/NWN/seasonal/reource_sheet_script_6/NWN_Seasonal_HER_NBI_DB_final.sql");
            final File CURL = new File("/Users/shahnawazshaikh/Desktop/NWN/seasonal/reource_sheet_script_6/NWN_Seasonal_HER_NBI_String_final.sh");

            final FileWriter fw = new FileWriter(SQL);
            final BufferedWriter br = new BufferedWriter(fw);
            final FileWriter fw2 = new FileWriter(CURL);
            final BufferedWriter br2 = new BufferedWriter(fw2);
            final FileInputStream fis = new FileInputStream(file);
            final XSSFWorkbook wb = new XSSFWorkbook(fis);

            final XSSFSheet sheet = wb.getSheetAt(4);
            System.out.println("sheet "+sheet.getSheetName());
            final Iterator<Row> itr = sheet.iterator();
            final int pilotID = 10097;
             Row row = itr.next();
            while (itr.hasNext()) {
             row = itr.next();
                try {
//                    final String required = row.getCell(1).getStringCellValue().trim().replaceAll("\n", " ");
//                    if (!required.equals("Y")) {
//                        System.out.println(required + "  " + row.getRowNum());
//                        continue;
//                    }
                    final String NBI_ID = row.getCell(0).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String NBI_TITLE = row.getCell(5).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String NBI_SHORT_TEXT = row.getCell(8).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String NBI_LONG_TEXT = row.getCell(7).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String circleIcon_link = row.getCell(9).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String rectangleIcon = row.getCell(10).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String INSIGHT_ID = row.getCell(3).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    final String INSIGHT_TEXT = row.getCell(6).getStringCellValue().trim().replaceAll("\n", " ")
                            .replaceAll("'", "\\u0027");
                    if (stNBI.contains(NBI_ID)) {
                        continue;
                    }
                    stNBI.add(NBI_ID);
                    final String titleText = "com.bidgely.cloud.core.lib.paper.nbi." + NBI_ID + ".title";
                    final String shortext = "com.bidgely.cloud.core.lib.paper.nbi." + NBI_ID + ".shortText";
                    final String longText = "com.bidgely.cloud.core.lib.paper.nbi." + NBI_ID + ".longText";
                    final String insightText = "com.bidgely.cloud.core.lib.paper.nbi." + INSIGHT_ID + ".insightText";
                    final String FIX_TEST = "INSERT INTO nbi_asset_data (entity_id, asset_id, asset_key, asset_value, asset_value_type, asset_type) VALUES (";

                    final String TITLE = FIX_TEST + "\"" + pilotID + "\", \"" + NBI_ID + "\", \"title\" , \""
                            + titleText + "\", \"STRING_RESOURCE\",\"PAPER_NBI\");";
                    final String SHORTTEXT = FIX_TEST + "\"" + pilotID + "\", \"" + NBI_ID + "\", \"shortText\", \""
                            + shortext + "\", \"STRING_RESOURCE\",\"PAPER_NBI\");";
                    final String LONGTEXT = FIX_TEST + "\"" + pilotID + "\", \"" + NBI_ID + "\", \"longText\" , \""
                            + longText + "\", \"STRING_RESOURCE\",\"PAPER_NBI\");";
                    final String CIRCLEICON = FIX_TEST + "\"" + pilotID + "\", \"" + NBI_ID + "\", \"circleIcon\", \""
                            + circleIcon_link + "\", \"IMAGE\",\"PAPER_NBI\");";
                    final String SQUAREICON = FIX_TEST + "\"" + pilotID + "\", \"" + NBI_ID + "\", \"squareIcon\", \""
                            + circleIcon_link + "\", \"IMAGE\",\"PAPER_NBI\");";
                    final String RECTANGLEICON = FIX_TEST + "\"" + pilotID + "\", \"" + NBI_ID
                            + "\", \"rectangleIcon\", \"" + rectangleIcon + "\", \"IMAGE\",\"PAPER_NBI\");";

                    br.write(TITLE);
                    br.write("\n");
                    br.write(SHORTTEXT);
                    br.write("\n");
                    br.write(LONGTEXT);
                    br.write("\n");
                    br.write(CIRCLEICON);
                    br.write("\n");
                    br.write(SQUAREICON);
                    br.write("\n");
                    br.write(RECTANGLEICON);
                    br.write("\n");
                    if (!stInsight.contains(INSIGHT_ID)) {
                        final String INSIGHTTEXT = FIX_TEST + "\"" + pilotID + "\", \"" + INSIGHT_ID
                                + "\", \"insightText\" , \"" + insightText + "\", \"STRING_RESOURCE\",\"PAPER_NBI\");";
                        br.write(INSIGHTTEXT);
                        br.write("\n");
                        stInsight.add(INSIGHT_ID);
                    }

                    final String stringLocale = "`curl -X PUT -H \"Authorization: Bearer $2\" -H \"Content-Type: application/json\" $1/2.1/stringResources/10097/resource/%s -d '[\n"
                            + "     { \"locale\": \"en_US\",\"text\": \"%s\",\"tags\": \"her_ui,LP_COMPONENT_HER\"}\n]'`";
                    br2.write(String.format(stringLocale, titleText, NBI_TITLE, NBI_TITLE, NBI_TITLE, NBI_TITLE,
                            NBI_TITLE, NBI_TITLE, NBI_TITLE, NBI_TITLE, NBI_TITLE, NBI_TITLE, NBI_TITLE, NBI_TITLE));
                    br2.write("\n");
                    br2.write(String.format(stringLocale, shortext, NBI_SHORT_TEXT, NBI_SHORT_TEXT, NBI_SHORT_TEXT,
                            NBI_SHORT_TEXT, NBI_SHORT_TEXT, NBI_SHORT_TEXT, NBI_SHORT_TEXT, NBI_SHORT_TEXT,
                            NBI_SHORT_TEXT, NBI_SHORT_TEXT, NBI_SHORT_TEXT, NBI_SHORT_TEXT));
                    br2.write("\n");
                    br2.write(String.format(stringLocale, longText, NBI_LONG_TEXT, NBI_LONG_TEXT, NBI_LONG_TEXT,
                            NBI_LONG_TEXT, NBI_LONG_TEXT, NBI_LONG_TEXT, NBI_LONG_TEXT, NBI_LONG_TEXT, NBI_LONG_TEXT,
                            NBI_LONG_TEXT, NBI_LONG_TEXT, NBI_LONG_TEXT));
                    br2.write("\n");
                    br2.write(String.format(stringLocale, insightText, INSIGHT_TEXT, INSIGHT_TEXT, INSIGHT_TEXT,
                            INSIGHT_TEXT, INSIGHT_TEXT, INSIGHT_TEXT, INSIGHT_TEXT, INSIGHT_TEXT, INSIGHT_TEXT,
                            INSIGHT_TEXT, INSIGHT_TEXT, INSIGHT_TEXT));
                    br2.write("\n");
                } catch (final Exception e) {
                    System.out.println("ERROR WHile processing Row " + row.getRowNum());
                    e.printStackTrace();
                    break;
                }

                System.out.println("SuccessFully Processed Row Number " + row.getRowNum());

            }

            br.close();
            br2.close();
        } catch (final Exception e) {
            System.out.println(e);
        }


    }
}