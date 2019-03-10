package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.Lista;
import model.data_structures.Queue;
import model.vo.VODaylyStatistic;
import model.vo.VOMovingViolations;
import model.vo.VOViolationCode;
import view.MovingViolationsManagerView;

public class Controller<T> {

	//-------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//-------------------------------------------------------------------------------------------------------------
	
	public static final String JANUARY = "./data/Moving_Violations_Issued_in_January_2018.csv";
	public static final String FEBRUARY = "./data/Moving_Violations_Issued_in_February_2018.csv";
	public static final String MARCH = "./data/Moving_Violations_Issued_in_March_2018.csv";
	public static final String APRIL = "./data/Moving_Violations_Issued_in_April_2018.csv";
	public static final String MAY = "./data/Moving_Violations_Issued_in_May_2018.csv";
	public static final String JUNE = "./data/Moving_Violations_Issued_in_June_2018.csv";
	public static final String JULY = "./data/Moving_Violations_Issued_in_July_2018.csv";
	public static final String AUGUST = "./data/Moving_Violations_Issued_in_August_2018.csv";
	public static final String SEPTEMBER = "./data/Moving_Violations_Issued_in_September_2018.csv";
	public static final String OCTOBER = "./data/Moving_Violations_Issued_in_October_2018.csv";
	public static final String NOVEMBER = "./data/Moving_Violations_Issued_in_November_2018.csv";
	public static final String DECEMBER = "./data/Moving_Violations_Issued_in_December_2018.csv";
	
	
	//-------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//-------------------------------------------------------------------------------------------------------------
	
	private MovingViolationsManagerView view;
	
	private static Lista <VOMovingViolations> movingViolations = new Lista<>();
	
	private String [] primerCuatrimestre = new String [4];
	private String [] segundoCuatrimestre = new String [4];
	private String [] tercerCuatrimestre = new String [4];
	
	
	//-------------------------------------------------------------------------------------------------------------
	// CONSTRUCTOR
	//-------------------------------------------------------------------------------------------------------------
	
	public Controller() {
		view = new MovingViolationsManagerView();
		
		primerCuatrimestre [0] = JANUARY;
		primerCuatrimestre [1] = FEBRUARY;
		primerCuatrimestre [2] = MARCH;
		primerCuatrimestre [3] = APRIL;
		
		segundoCuatrimestre [0] = MAY;
		segundoCuatrimestre [1] = JUNE;
		segundoCuatrimestre [2] = JULY;
		segundoCuatrimestre [3] = AUGUST;
		
		tercerCuatrimestre [0] = SEPTEMBER;
		tercerCuatrimestre [1] = OCTOBER;
		tercerCuatrimestre [2] = NOVEMBER;
		tercerCuatrimestre [3] = DECEMBER;
	}
	
	//-------------------------------------------------------------------------------------------------------------
	// MÉTODOS
	//-------------------------------------------------------------------------------------------------------------
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean fin=false;
		Controller controller = new Controller();
		
		while(!fin)
		{
			view.printMenu();
			
			int option = sc.nextInt();
			
			switch(option)
			{
				case 0:
					view.printMessage("Ingrese el cuatrimestre (1, 2 o 3)");
					int numeroCuatrimestre = sc.nextInt();
					controller.loadMovingViolations(numeroCuatrimestre);
					break;
				
				case 1:
					Lista <VOMovingViolations> listaRepetidos = controller.verifyObjectIDIsUnique();
					for (int i = 0; i<listaRepetidos.size(); i++)
					{
						System.out.println(listaRepetidos.getPos(i).getObjectId());
					}
					break;
					
				case 2:
					
					view.printMessage("Ingrese la fecha con hora inicial (Ej : 28/03/2017T15:30:20)");
					LocalDateTime fechaInicialReq2A = convertirFecha_Hora_LDT(sc.next());
					
					view.printMessage("Ingrese la fecha con hora final (Ej : 28/03/2017T15:30:20)");
					LocalDateTime fechaFinalReq2A = convertirFecha_Hora_LDT(sc.next());
					
					IQueue<VOMovingViolations> resultados2 = controller.getMovingViolationsInRange(fechaInicialReq2A, fechaFinalReq2A);
					
					view.printMovingViolationsReq2(resultados2);
					
					break;
					
				case 3:
					
					view.printMessage("Ingrese el VIOLATIONCODE (Ej : T210)");
					String violationCode3 = sc.next();
					
					double [] promedios3 = controller.avgFineAmountByViolationCode(violationCode3);
					
					view.printMessage("FINEAMT promedio sin accidente: " + promedios3[0] + ", con accidente:" + promedios3[1]);
					break;
						
					
				case 4:
					
					view.printMessage("Ingrese el ADDRESS_ID");
					String addressId4 = sc.next();

					view.printMessage("Ingrese la fecha con hora inicial (Ej : 28/03/2017)");
					LocalDate fechaInicialReq4A = convertirFecha(sc.next());
					
					view.printMessage("Ingrese la fecha con hora final (Ej : 28/03/2017)");
					LocalDate fechaFinalReq4A = convertirFecha(sc.next());
					
					IStack<VOMovingViolations> resultados4 = controller.getMovingViolationsAtAddressInRange(addressId4, fechaInicialReq4A, fechaFinalReq4A);
					
					view.printMovingViolationsReq4(resultados4);
					
					break;
					
				case 5:
					view.printMessage("Ingrese el limite inferior de FINEAMT  (Ej: 50)");
					double limiteInf5 = sc.nextDouble();
					
					view.printMessage("Ingrese el limite superior de FINEAMT  (Ej: 50)");
					double limiteSup5 = sc.nextDouble();
					
					IQueue<VOViolationCode> violationCodes = controller.violationCodesByFineAmt(limiteInf5, limiteSup5);
					view.printViolationCodesReq5(violationCodes);
					break;
				
				case 6:
					
					view.printMessage("Ingrese el limite inferior de TOTALPAID (Ej: 200)");
					double limiteInf6 = sc.nextDouble();
					
					view.printMessage("Ingrese el limite superior de TOTALPAID (Ej: 200)");
					double limiteSup6 = sc.nextDouble();
					
					view.printMessage("Ordenar Ascendentmente: (Ej: true)");
					boolean ascendente6 = sc.nextBoolean();				
					
					IStack<VOMovingViolations> resultados6 = controller.getMovingViolationsByTotalPaid(limiteInf6, limiteSup6, ascendente6);
					view.printMovingViolationReq6(resultados6);
					break;
					
				case 7:
					
					view.printMessage("Ingrese la hora inicial (Ej: 23)");
					int horaInicial7 = sc.nextInt();
					
					view.printMessage("Ingrese la hora final (Ej: 23)");
					int horaFinal7 = sc.nextInt();
					
					IQueue<VOMovingViolations> resultados7 = controller.getMovingViolationsByHour(horaInicial7, horaFinal7);
					view.printMovingViolationsReq7(resultados7);
					break;
					
				case 8:
					
					view.printMessage("Ingrese el VIOLATIONCODE (Ej : T210)");
					String violationCode8 = sc.next();
					
					double [] resultado8 = controller.avgAndStdDevFineAmtOfMovingViolation(violationCode8);
					
					view.printMessage("FINEAMT promedio: " + resultado8[0] + ", desviación estandar:" + resultado8[1]);
					break;
					
				case 9:
					
					view.printMessage("Ingrese la hora inicial (Ej: 23)");
					int horaInicial9 = sc.nextInt();
					
					view.printMessage("Ingrese la hora final (Ej: 23)");
					int horaFinal9 = sc.nextInt();
					
					int resultado9 = controller.countMovingViolationsInHourRange(horaInicial9, horaFinal9);
					
					view.printMessage("Número de infracciones: " + resultado9);
					break;
				
				case 10:
					view.printMovingViolationsByHourReq10();
					break;
					
				case 11:
					view.printMessage("Ingrese la fecha inicial (Ej : 28/03/2017)");
					LocalDate fechaInicial11 = convertirFecha(sc.next());
					
					view.printMessage("Ingrese la fecha final (Ej : 28/03/2017)");
					LocalDate fechaFinal11 = convertirFecha(sc.next());
					
					double resultados11 = controller.totalDebt(fechaInicial11, fechaFinal11);
					view.printMessage("Deuda total "+ resultados11);
					break;
				
				case 12:	
					view.printTotalDebtbyMonthReq12();
					break;
					
					
				case 13:	
					view.printTotalData(movingViolations);
					break;
					
				case 14:	
					fin=true;
					sc.close();
					break;
			}
		}

	}
	
	
	public void loadMovingViolations(int numeroCuatrimestre) {

		String archivoACargar = "";

		if (numeroCuatrimestre == 1) {
			int contadorCuatrimestre = 0;

			for (int i = 0; i < 4; i++) {
				int contadorMes = 0;
				archivoACargar = primerCuatrimestre[i];
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				try {
					br = new BufferedReader(new FileReader(archivoACargar));
					line = br.readLine();

					while ((line = br.readLine()) != null) {
						String[] datos = line.split(cvsSplitBy);

						String newObjectId = (datos[0].replace("\"", ""));
						String newEmptyRow = datos[1];
						String newLocation = datos[2];
						String newAddress_id = datos[3];
						String newStreetsegid = datos[4];

						double newXcoord = Double.parseDouble(datos[5]);
						double newYcoord = Double.parseDouble(datos[6]);

						String newTickettype = datos[7];

						int newFineamt = Integer.parseInt(datos[8]);
						String newTotalpaid = datos[9];

						String newPenalty1 = datos[10];
						String newPenalty2 = datos[11];
						String newAccidentindicator = datos[12];

						String[] d1 = datos[13].split("-");
						char[] parseo = d1[2].toCharArray();

						String anio = d1[0];
						String mes = d1[1];

						String dia = (parseo[0] + "") + (parseo[1] + "");
						String hora = (parseo[3] + "") + (parseo[4] + "");
						String minuto = (parseo[6] + "") + (parseo[7] + "");
						String segundo = (parseo[9] + "") + (parseo[10] + "");

						GregorianCalendar time = new GregorianCalendar(Integer.parseInt(anio), Integer.parseInt(mes)-1,
								Integer.parseInt(dia), Integer.parseInt(hora), Integer.parseInt(minuto),
								Integer.parseInt(segundo));

						GregorianCalendar newTicketissuedate = time;
						String newViolationcode = datos[14];
						String newViolationdesc = datos[15];

						VOMovingViolations newMovingViolation = new VOMovingViolations(newObjectId, newEmptyRow,
								newLocation, newAddress_id, newStreetsegid, newXcoord, newYcoord, newTickettype,
								newFineamt, newTotalpaid, newPenalty1, newPenalty2, newAccidentindicator,
								newTicketissuedate, newViolationcode, newViolationdesc);

			

						movingViolations.addLast(newMovingViolation);
						
						System.out.println(	movingViolations.size());
					

						contadorMes++;
						
					}
					contadorCuatrimestre = contadorCuatrimestre + contadorMes;
					System.out.println("El número de datos cargado es el mes es" + " : " + contadorMes);
					

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				catch (IOException e) {
					e.printStackTrace();
				}

				finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
			
			
			System.out.println("El número total de datos cargado en el primer cuatrimestre es " + " : " + contadorCuatrimestre);
		}
		
		else if (numeroCuatrimestre == 2) {
			int contadorCuatrimestre = 0;

			for (int i = 0; i < 4; i++) {
				int contadorMes = 0;
				archivoACargar = segundoCuatrimestre[i];
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				try {
					br = new BufferedReader(new FileReader(archivoACargar));
					line = br.readLine();

					while ((line = br.readLine()) != null) {
						String[] datos = line.split(cvsSplitBy);

						String newObjectId = (datos[0].replace("\"", ""));
						String newEmptyRow = datos[1];
						String newLocation = datos[2];
						String newAddress_id = datos[3];
						String newStreetsegid = datos[4];

						double newXcoord = Double.parseDouble(datos[5]);
						double newYcoord = Double.parseDouble(datos[6]);

						String newTickettype = datos[7];

						int newFineamt = Integer.parseInt(datos[8]);
						String newTotalpaid = datos[9];

						String newPenalty1 = datos[10];
						String newPenalty2 = datos[11];
						String newAccidentindicator = datos[12];

						String[] d1 = datos[13].split("-");
						char[] parseo = d1[2].toCharArray();

						String anio = d1[0];
						String mes = d1[1];

						String dia = (parseo[0] + "") + (parseo[1] + "");
						String hora = (parseo[3] + "") + (parseo[4] + "");
						String minuto = (parseo[6] + "") + (parseo[7] + "");
						String segundo = (parseo[9] + "") + (parseo[10] + "");

						GregorianCalendar time = new GregorianCalendar(Integer.parseInt(anio), Integer.parseInt(mes)-1,
								Integer.parseInt(dia), Integer.parseInt(hora), Integer.parseInt(minuto),
								Integer.parseInt(segundo));

						GregorianCalendar newTicketissuedate = time;
						String newViolationcode = datos[14];
						String newViolationdesc = datos[15];

						VOMovingViolations newMovingViolation = new VOMovingViolations(newObjectId, newEmptyRow,
								newLocation, newAddress_id, newStreetsegid, newXcoord, newYcoord, newTickettype,
								newFineamt, newTotalpaid, newPenalty1, newPenalty2, newAccidentindicator,
								newTicketissuedate, newViolationcode, newViolationdesc);

						System.out.println(newMovingViolation.getObjectId());
						System.out.println(newTicketissuedate.getTime());

						movingViolations.addLast(newMovingViolation);

						contadorMes++;
						
					}
					contadorCuatrimestre = contadorCuatrimestre + contadorMes;
					System.out.println("El número de datos cargado es el mes es" + " : " + contadorMes);
					

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				catch (IOException e) {
					e.printStackTrace();
				}

				finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
			
			
			System.out.println("El número total de datos cargado en el segundo cuatrimestre es " + " : " + contadorCuatrimestre);
		}
		
		else if (numeroCuatrimestre == 3) {
			int contadorCuatrimestre = 0;

			for (int i = 0; i < 4; i++) {
				int contadorMes = 0;
				archivoACargar = tercerCuatrimestre[i];
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				try {
					br = new BufferedReader(new FileReader(archivoACargar));
					line = br.readLine();

					while ((line = br.readLine()) != null) {
						String[] datos = line.split(cvsSplitBy);

						String newObjectId = (datos[0].replace("\"", ""));
						String newEmptyRow = datos[1];
						String newLocation = datos[2];
						String newAddress_id = datos[3];
						String newStreetsegid = datos[4];

						double newXcoord = Double.parseDouble(datos[5]);
						double newYcoord = Double.parseDouble(datos[6]);

						String newTickettype = datos[7];

						int newFineamt = Integer.parseInt(datos[8]);
						String newTotalpaid = datos[9];

						String newPenalty1 = datos[10];
						String newPenalty2 = datos[11];
						String newAccidentindicator = datos[12];
						
						String[] d1 = datos[13].split("-");
						char[] parseo = d1[2].toCharArray();

						String anio = d1[0];
						String mes = d1[1];

						String dia = (parseo[0] + "") + (parseo[1] + "");
						String hora = (parseo[3] + "") + (parseo[4] + "");
						String minuto = (parseo[6] + "") + (parseo[7] + "");
						String segundo = (parseo[9] + "") + (parseo[10] + "");

						GregorianCalendar time = new GregorianCalendar(Integer.parseInt(anio), Integer.parseInt(mes)-1,
								Integer.parseInt(dia), Integer.parseInt(hora), Integer.parseInt(minuto),
								Integer.parseInt(segundo));

						GregorianCalendar newTicketissuedate = time;
						
						String newViolationcode = datos[14];
						String newViolationdesc = datos[15];

						VOMovingViolations newMovingViolation = new VOMovingViolations(newObjectId, newEmptyRow,
								newLocation, newAddress_id, newStreetsegid, newXcoord, newYcoord, newTickettype,
								newFineamt, newTotalpaid, newPenalty1, newPenalty2, newAccidentindicator,
								newTicketissuedate, newViolationcode, newViolationdesc);

						System.out.println(newMovingViolation.getObjectId());
						System.out.println(newTicketissuedate.getTime());

						movingViolations.addLast(newMovingViolation);

						contadorMes++;
						
					}
					contadorCuatrimestre = contadorCuatrimestre + contadorMes;
					System.out.println("El número de datos cargado es el mes es" + " : " + contadorMes);
					

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				catch (IOException e) {
					e.printStackTrace();
				}

				finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
			
			
			System.out.println("El número total de datos cargado en el tercer cuatrimestre es " + " : " + contadorCuatrimestre);

		}
		System.out.println("El número total de datos cargado en el tercer cuatrimestre es " + " : " + movingViolations.size());

	}
	
	/**
	 * 1A- Verificar	 que	OBJECTID	 es	 en	 realidad	 un	 identificador	 único. Verificar	 si	 todos	los	
	OBJECTID	 son	 únicos.	Mostrar	 un	 texto	 indicando	si	No	 hay	 ningún	 OBJECTID repetido	
	entre	 todos	 los	meses. Si hay	 OBJECTID	 repetidos,	 mostrar	 aquellos	 que	 se	 encuentren	
	repetidos.
	 * @return
	 */
	public Lista <VOMovingViolations> verifyObjectIDIsUnique() {

		Lista <VOMovingViolations> respuesta = new Lista<VOMovingViolations>();
		
		for (int i = 0; i < movingViolations.size(); i++) 
		{
			for (int j = i; j < movingViolations.size(); j++) 
			{
				if(movingViolations.getPos(i).equals(movingViolations.getPos(j)))
				{
					respuesta.addLast(movingViolations.getPos(i));
				}
			}
			 
		}
		return respuesta;
		
	}
	
	
	
	public IQueue <VODaylyStatistic> getDailyStatistics () {
		return null;
	}
	
	public IStack <VOMovingViolations> nLastAccidents(int n) {
		return null;
	}

	

	public IQueue<VOMovingViolations> getMovingViolationsInRange(LocalDateTime fechaInicial,
			LocalDateTime fechaFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public double[] avgFineAmountByViolationCode(String violationCode3) {
		return new double [] {0.0 , 0.0};
	}

	public IStack<VOMovingViolations> getMovingViolationsAtAddressInRange(String addressId,
			LocalDate fechaInicial, LocalDate fechaFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public IQueue<VOViolationCode> violationCodesByFineAmt(double limiteInf5, double limiteSup5) {
		// TODO Auto-generated method stub
		return null;
	}

	public IStack<VOMovingViolations> getMovingViolationsByTotalPaid(double limiteInf6, double limiteSup6,
			boolean ascendente6) {
		// TODO Auto-generated method stub
		return null;
	}

	public IQueue<VOMovingViolations> getMovingViolationsByHour(int horaInicial7, int horaFinal7) {
		// TODO Auto-generated method stub
		return null;
	}

	public double[] avgAndStdDevFineAmtOfMovingViolation(String violationCode8) {
		// TODO Auto-generated method stub
		return new double [] {0.0 , 0.0};
	}

	public int countMovingViolationsInHourRange(int horaInicial9, int horaFinal9) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double totalDebt(LocalDate fechaInicial11, LocalDate fechaFinal11) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/**
	 * Convertir fecha a un objeto LocalDate
	 * @param fecha fecha en formato dd/mm/aaaa con dd para dia, mm para mes y aaaa para agno
	 * @return objeto LD con fecha
	 */
	private static LocalDate convertirFecha(String fecha)
	{
		return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	
	/**
	 * Convertir fecha y hora a un objeto LocalDateTime
	 * @param fecha fecha en formato dd/mm/aaaaTHH:mm:ss con dd para dia, mm para mes y aaaa para agno, HH para hora, mm para minutos y ss para segundos
	 * @return objeto LDT con fecha y hora integrados
	 */
	private static LocalDateTime convertirFecha_Hora_LDT(String fechaHora)
	{
		return LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss"));
	}

}
