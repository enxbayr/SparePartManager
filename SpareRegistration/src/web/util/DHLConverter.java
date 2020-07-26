package web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

public class DHLConverter extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7656620981331919866L;

	public DHLConverter() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String header = null;
		String[][] headers = {
				{
						"★Задаргаа бүрийн тээврийн баримтын дугаар.\n(20 тэмдэгтээс ихгүй)",
						"★Барааны нэр\n(200 тэмдэгтээс ихгүй байна)",
						"Илгээгч","","",
						"Хүлээн авагч","","",
						"★Баглаа боодлын тоо\n(Хамгийн ихдээ 16 оронтой тоо)",
						"★Баглаа боодлын төрөл",
						"★Бохир жин(КГ)\n(Хамгийн ихдээ 15 оронтой тоо, таслалаас хойшхи 2 орон)",
						"Тээврийн зардал\n\"Агаар\"-ын үед","",
						"Measurement\nOnly Transportation is \"Air\"\n(Under 16digit,\n3 decimal digits)",
						"Measurement Unit\nOnly Transportation is \"Air\"",
						"Dangerous Goods Code",
						"Export Declaration No.\nOnly Manifest Classification is \"Export\"\n(17Bytes)",
						"Invoice Price","","","","",""},
						
				{ 		"","",
						"★Нэр\n(50 тэмдэгтээс ихгүй байна)",
						"Утас\n(50 тэмдэгт)",
						"★Хаяг\n(200 тэмдэгтээс ихгүй байна)",
						"★Нэр\n(50 тэмдэгтээс ихгүй байна)",
						"Утас\n(50 тэмдэгт)",
						"★Хаяг\n(200 тэмдэгтээс ихгүй байна)", "", "", "", "Үнэ\n(16 оронтой тоо, \n2 бутархай орон)","Fare\nCurrency",
						"","","","",
						"Amount 1\n(Under 16digit,\n 2 decimal digits)","Amount 1\nCurrency",
						"Amount 2\n(Under 16digit,\n 2 decimal digits)","Amount 2\nCurrency",
						"Amount 3\n(Under 16digit,\n 2 decimal digits)","Amount 3\nCurrency"
				} 
		};

		String[][] codes = {
				{ "CT", "Carton" },
				{ "BA", "Barrel" },
				{ "BC", "Bottlecrate" },
				{ "BE", "Bundle" },
				{ "BG", "Bag" },
				{ "BJ", "Bucket" },
				{ "BL", "Bale" },
				{ "BR", "Bar" },
				{ "BV", "Bottle, bulbous" },
				{ "CA", "Can" },
				{ "CH", "Chest" },
				{ "CJ", "Coffin" },
				{ "CL", "Coil" },
				{ "CR", "Wooden Box, Wooden Case" },
				{ "CS", "Cassette" },
				{ "DR", "Drum" },
				{ "HH", "Hand Baggage" },
				{ "IZ", "Ingots" },
				{ "NT", "Net" },
				{ "OU", "Non-Exterior Packaging Unit" },
				{ "PG", "Plate" },
				{ "PI", "Pipe" },
				{ "PU", "Traypack" },
				{ "RL", "Reel" },
				{ "RO", "Roll" },
				{ "SK", "Skeletoncase" },
				{ "GT", "Extra Countable Item" },
				{ "VG", "Bulk,gas(at 1031 mbar 15 oC)" },
				{ "VL", "Bulk,liquid(at normal temperature/pressure)" },
				{ "VO", "Bulk, solid, large particles('nodules')" },
				{ "VQ", "Bulk, gas(liquefied at abnormal temperature/pressure)" },
				{ "VR", "Bulk, solid, granular particles('grains')" },
				{ "VY", "Bulk, fine particles('powder')" },
				{ "VT", "Extra Bulk Item" }, };

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setSizeThreshold(3 * 1024 * 1024);
		//fileItemFactory.setRepository(new File("/Volumes/D/"));
		fileItemFactory.setRepository(new File("/home/dhlconverter/"));

		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

		try {

			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();

			int success = 0;
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();

				if (item.isFormField()) {
				} else {

					if ("text/plain".equals(item.getContentType())) {
						/*
						 * System.out.println("Field Name = " +
						 * item.getFieldName() + ", File Name = " +
						 * item.getName() + ", Content type = " +
						 * item.getContentType() + ", File Size = " +
						 * item.getSize());
						 */
						// handle upload files

						BufferedReader br = new BufferedReader(
								new InputStreamReader(item.getInputStream()));
						Workbook wb = new HSSFWorkbook();
						DataFormat format = wb.createDataFormat();

						Sheet elist = wb.createSheet("House BL List");

						Sheet code = wb.createSheet("Code Name");
						
						Font font = wb.createFont();
						font.setFontHeightInPoints((short) 11);
						font.setFontName("Calibri");

						Row cr = code.createRow(1);

						Cell cc = cr.createCell(1);

						cc.setCellValue("Type of Packages");
						cc.setCellStyle(createStyleCT(wb,
								CellStyle.BORDER_NONE,
								IndexedColors.GREY_25_PERCENT.getIndex(),font));
						code.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

						cr = code.createRow(2);

						cc = cr.createCell(1);
						cc.setCellStyle(createStyleCT(wb,
								CellStyle.BORDER_THIN,
								IndexedColors.GREY_80_PERCENT.getIndex(),font));
						cc.setCellValue("Code");

						cc = cr.createCell(2);
						cc.setCellStyle(createStyleCT(wb,
								CellStyle.BORDER_THIN,
								IndexedColors.GREY_80_PERCENT.getIndex(),font));
						cc.setCellValue("Name");

						int idx = 3;
						for (int i = 0; i < codes.length; i++) {
							cr = code.createRow(idx);

							cc = cr.createCell(1);
							cc.setCellStyle(createStyleCT(wb,
									CellStyle.BORDER_THIN,
									IndexedColors.GREY_80_PERCENT.getIndex(),font));
							cc.setCellValue(codes[i][0]);

							cc = cr.createCell(2);
							cc.setCellStyle(createStyleSE(wb,
									CellStyle.BORDER_THIN,
									IndexedColors.GREY_80_PERCENT.getIndex(),font));
							cc.setCellValue(codes[i][1]);
							idx++;
						}

						code.autoSizeColumn(2);

						for (int i = 0; i < 2; i++) {
							Row r = elist.createRow(i);
							r.setHeightInPoints((4 * elist
									.getDefaultRowHeightInPoints()));
							for (int j = 0; j < 23; j++) {

								Cell c = r.createCell(j);
								c.setCellStyle(createStyle(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.BLACK.getIndex(),
										IndexedColors.PALE_BLUE.getIndex(),font));
								c.setCellValue(headers[i][j]);
							}
						}

						
						elist.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
						elist.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
						
						elist.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
						elist.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
						
						elist.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
						elist.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));
						elist.addMergedRegion(new CellRangeAddress(0, 1, 10, 10));
						
						elist.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
						
						elist.addMergedRegion(new CellRangeAddress(0, 1, 13, 13));
						elist.addMergedRegion(new CellRangeAddress(0, 1, 14, 14));
						elist.addMergedRegion(new CellRangeAddress(0, 1, 15, 15));
						elist.addMergedRegion(new CellRangeAddress(0, 1, 16, 16));
						
						elist.addMergedRegion(new CellRangeAddress(0, 0, 17, 22));

						elist.setColumnWidth(0, 4000);
						elist.setColumnWidth(1, 6000);
						elist.setColumnWidth(2, 5000);
						elist.setColumnWidth(3, 5000);
						elist.setColumnWidth(4, 10000);
						elist.setColumnWidth(5, 5000);
						elist.setColumnWidth(6, 5000);
						elist.setColumnWidth(7, 10000);
						elist.setColumnWidth(8, 5000);
						elist.setColumnWidth(9, 5000);
						elist.setColumnWidth(10, 5000);
						elist.setColumnWidth(11, 5000);
						elist.setColumnWidth(12, 5000);
						elist.setColumnWidth(13, 5000);
						elist.setColumnWidth(14, 5000);
						elist.setColumnWidth(15, 5000);
						elist.setColumnWidth(16, 5000);
						elist.setColumnWidth(17, 5000);
						elist.setColumnWidth(18, 3000);
						elist.setColumnWidth(19, 5000);
						elist.setColumnWidth(10, 3000);
						elist.setColumnWidth(21, 5000);
						elist.setColumnWidth(22, 3000);

						String line = null;
						int hd = 2;
						while ((line = br.readLine()) != null) {
							// System.out.println(line.length() + ":" + line);

							if (header == null
									&& line
											.matches("\\AUPLIFT\\s+TRAN\\s+DEST.+")) {
								line = br.readLine();
								line = br.readLine();
								header = line;
							}

							if (line
									.matches("\\A\\d{1,}\\s+\\d{1,}\\s+\\d{1,}.+")
									|| line
											.matches("\\A\\d{1,}\\s+\\d{1,}\\/\\d{1,2}.+")) {

								String hawbno = line.substring(4, 14);
								String[] str = line.substring(14, 30).trim()
										.split("\\s+");
								String weight = str[1];
								try {
									DecimalFormat fmt = new DecimalFormat("0.0");
									weight = fmt.format(Double.valueOf(weight)
											.doubleValue());
								} catch (Exception e) {
									weight = "0.0";
								}
								

								String currency = line.substring(46, 49).trim();
								String price = line.substring(49, 62).trim();
								try {
									DecimalFormat fmt = new DecimalFormat("0.00");
									price = fmt.format(Double.valueOf(price)
											.doubleValue());
								} catch (Exception e) {
									price = "0.00";
								}

								String shipper_name = line.substring(62, 82)
										.trim();
								String dist_name = line.substring(83, 103)
										.trim();

								String shipper_address = "";
								String dist_address = "";
								String content = "";

								content = line.substring(30, 45).trim();

								for (int i = 0; i < 4; i++) {
									line = br.readLine();
									content += content.endsWith(" ") ? line
											.substring(30, 45).trim() : " "
											+ line.substring(30, 45).trim();
									shipper_address += shipper_address
											.endsWith(" ") ? line.substring(62,
											82).trim() : " "
											+ line.substring(62, 82).trim();
									dist_address += dist_address.endsWith(" ") ? line
											.substring(83, 103).trim()
											: " "
													+ line.substring(83, 103)
															.trim();
								}

								Row r = elist.createRow(hd);
								Cell c1 = r.createCell(0);
								c1.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c1.setCellValue(hawbno);
								
								Cell c2 = r.createCell(1);
								c2.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c2.setCellValue(""+content);
								
								Cell c3 = r.createCell(2);
								c3.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c3.setCellValue("" + shipper_name);
								Cell c4 = r.createCell(3);
								c4.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c4.setCellValue("");
								Cell c5 = r.createCell(4);
								c5.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c5.setCellValue("" + shipper_address);
								
								
								Cell c6 = r.createCell(5);
								c6.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c6.setCellValue("" + dist_name);
								Cell c7 = r.createCell(6);
								c7.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c7.setCellValue("");
								Cell c8 = r.createCell(7);
								c8.setCellStyle(createStyleSE(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c8.setCellValue("" + dist_address);
								Cell c9 = r.createCell(8);
								c9.setCellStyle(createStyleCT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								if (hd == 2) {
									String hus = detectHUS(header);
									c9.setCellValue(hus);
								} else {
									c9.setCellValue("0");
								}

								Cell c10 = r.createCell(9);
								c10.setCellStyle(createStyleCT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(),font));
								c10.setCellValue("CT");
								
								Cell c11 = r.createCell(10);
								c11.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c11.setCellValue(weight + "0");
								
								Cell c12 = r.createCell(11);
								c12.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c12.setCellValue("");
								
								Cell c13 = r.createCell(12);
								c13.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c13.setCellValue("");
								
								Cell c14 = r.createCell(13);
								c14.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c14.setCellValue("");
								
								Cell c15 = r.createCell(14);
								c15.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c15.setCellValue("");
								
								
								Cell c16 = r.createCell(15);
								c16.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c16.setCellValue("");
								
								
								Cell c17 = r.createCell(16);
								c17.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c17.setCellValue("");
								Cell c18 = r.createCell(17);
								c18.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c18.setCellValue(""+price);
								Cell c19 = r.createCell(18);
								c19.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c19.setCellValue(""+currency);
								
								Cell c20 = r.createCell(19);
								c20.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c20.setCellValue("");
								Cell c21 = r.createCell(20);
								c21.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c21.setCellValue("");
								Cell c22 = r.createCell(21);
								c22.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c22.setCellValue("");
								Cell c23 = r.createCell(22);
								c23.setCellStyle(createStyleWT(wb,
										CellStyle.BORDER_THIN,
										IndexedColors.GREY_25_PERCENT
												.getIndex(), format));
								c23.setCellValue("");
								
								
								hd++;
							}

						}
						String filename = detectFname(header);

						response.setHeader("Content-type",
								"application/octetstream");
						response.setHeader("Content-disposition",
								"attachment; filename=" + filename + ".xls");

						HSSFDataValidation dataValidation = new HSSFDataValidation(
								new CellRangeAddressList(2, 1500, 4, 4),
								DVConstraint
										.createFormulaListConstraint("'Code'!$B$4:$B$37"));
						elist.addValidationData(dataValidation);

						ServletOutputStream os = response.getOutputStream();
						wb.write(os);
						os.flush();
						os.close();
						success = 1;
					} else if (item.getSize() > 0) {
						success = 2;
					}
				}
			}

			switch (success) {
			case 0:
				response.sendRedirect("converter.jsp?error=Select file");
				break;
			case 1:
				break;
			case 2:
				response
						.sendRedirect("converter.jsp?error=Please upload only text file");
				break;
			default:
				response.sendRedirect("converter.jsp?error=Unknown error");
				break;
			}

		} catch (FileUploadException ex) {
			log("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log("Error encountered while uploading file", ex);
		}

	}

	public static CellStyle createStyle(Workbook wb, short bs, short color,
			short bcolor, Font font) {
		/*
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
		*/
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(bs);
		style.setBottomBorderColor(color);
		style.setBorderLeft(bs);
		style.setLeftBorderColor(color);
		style.setBorderRight(bs);
		style.setRightBorderColor(color);
		style.setBorderTop(bs);
		style.setTopBorderColor(color);
		style.setWrapText(true);
		//style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		style.setFillBackgroundColor(bcolor);
		style.setFillForegroundColor(bcolor);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}

	public static CellStyle createStyleSE(Workbook wb, short bs, short color, Font font) {

		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(bs);
		style.setBottomBorderColor(color);
		style.setBorderLeft(bs);
		style.setLeftBorderColor(color);
		style.setBorderRight(bs);
		style.setRightBorderColor(color);
		style.setBorderTop(bs);
		style.setFont(font);
		style.setTopBorderColor(color);
		style.setWrapText(false);
		style.setAlignment(CellStyle.ALIGN_GENERAL);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		return style;
	}

	public static CellStyle createStyleCT(Workbook wb, short bs, short color, Font font) {
		
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(bs);
		style.setBottomBorderColor(color);
		style.setBorderLeft(bs);
		style.setLeftBorderColor(color);
		style.setBorderRight(bs);
		style.setRightBorderColor(color);
		style.setBorderTop(bs);
		style.setFont(font);
		style.setTopBorderColor(color);
		style.setWrapText(false);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		return style;
	}

	public static CellStyle createStyleWT(Workbook wb, short bs, short color,
			DataFormat df) {
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(bs);
		style.setBottomBorderColor(color);
		style.setBorderLeft(bs);
		style.setLeftBorderColor(color);
		style.setBorderRight(bs);
		style.setFont(font);
		style.setRightBorderColor(color);
		style.setBorderTop(bs);
		style.setTopBorderColor(color);
		style.setWrapText(false);
		style.setAlignment(CellStyle.ALIGN_GENERAL);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		style.setDataFormat(df.getFormat("0.00"));

		return style;
	}

	public static String detectFname(String line) {
		String fn = "";
		String[] str = line.split("\\s+");
		if (str.length == 17) {
			fn = str[3] + "_" + str[4] + str[5] + "_"
					+ str[15].replace("/", ".");
		} else if (str.length == 18) {
			fn = str[4] + "_" + str[5] + str[6] + "_"
					+ str[16].replace("/", ".");
		}
		return fn;
	}

	public static String detectHUS(String line) {
		String fn = "";
		String[] str = line.split("\\s+");
		/*
		 * for(int i = 0; i < str.length; i ++) { System.out.println(i + ": " +
		 * str[i]); }
		 */
		// System.out.println();
		if (str.length == 17) {
			fn = str[7];
		} else if (str.length == 18) {
			fn = str[8];
		}
		return fn;
	}
}
