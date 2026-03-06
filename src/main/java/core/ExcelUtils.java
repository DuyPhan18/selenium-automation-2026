package core;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    /**
     * Phương thức này đọc dữ liệu từ file Excel và trả về mảng hai chiều String.
     *
     * @param filePath       Đường dẫn tới file Excel
     * @param isGetFirstRow  Nếu true, bao gồm hàng đầu tiên trong dữ liệu trả về; nếu false, bỏ qua hàng đầu tiên
     * @return Mảng hai chiều String chứa dữ liệu từ file Excel
     */
    public static synchronized String[][] getTableArray(String filePath, String sheetName, boolean isGetFirstRow){
        String[][] data = null;
        File file = new File(filePath);

        // KIỂM TRA FILE TRƯỚC KHI ĐỌC
        if (!file.exists()) {
            System.err.println("❌ ERROR: File not found at path: " + file.getAbsolutePath());
            // Thay vì return null, bạn nên ném ngoại lệ để TestNG dừng lại ngay
            throw new RuntimeException("Excel file not found at: " + filePath);
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new XSSFWorkbook(fileInputStream);
            Sheet sheet = wb.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet " + sheetName + " does not exist in " + filePath);
            }

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();
            data = new String[rows][cols];
            int startRow = 0;

            if (isGetFirstRow == false){
                startRow = 1;
                data = new String[rows - 1][cols];
            }

            for (int r = startRow; r < rows; r++){
                Row row = sheet.getRow(r);
                for (int c = 0; c < cols; c++){
                    Cell cell = row.getCell(c);
                    if (startRow == 1){
                        data[r - 1][c] = getCellValueAsString(cell);
                    } else
                data[r][c] = getCellValueAsString(cell);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return data;
    }
    /**
     * Phương thức này trả về giá trị của ô dưới dạng String.
     *
     * @param cell Ô cần lấy giá trị
     * @return Giá trị của ô dưới dạng String
     */

    private static String getCellValueAsString(Cell cell) {
        if (cell == null){
            return "";
        }
        // Khởi tạo DataFormatter để lấy giá trị như hiển thị trong Excel
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
//        switch (cell.getCellType()){
//            case STRING:
//                return cell.getStringCellValue();
//            case NUMERIC:
//                return String.valueOf(cell.getNumericCellValue());
//            case BOOLEAN:
//                return String.valueOf(cell.getBooleanCellValue());
//            default:
//                return "";
//        }
    }
    /**
     * Phương thức này ghi dữ liệu vào file Excel.
     *
     * @param filePath  Đường dẫn tới file Excel
     * @param sheetName Tên sheet cần ghi dữ liệu
     * @param data      Mảng String chứa dữ liệu cần ghi
     */
    public static void writeDataToExcel(String filePath, String sheetName, String[] data,String[][] headers) {
        Workbook workbook = null;
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(filePath);

            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
                createHeaderRow(sheet, headers[0]);
            }

            int rowNum = sheet.getLastRowNum() ;
            Row row = sheet.createRow(rowNum+1);

            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i]);
            }
            if (sheet.getLastRowNum() == 0) {
                createHeaderRow(sheet, headers[0]);
            }

            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Phương thức này tạo header row cho sheet dựa trên mảng headers được truyền vào.
     *
     * @param sheet   Sheet cần tạo header row
     * @param headers Mảng String chứa headers cho sheet
     */
    private static void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }
}
