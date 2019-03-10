package model.vo;

import java.util.GregorianCalendar;

/**
 * Representation of a Trip object
 */
public class VOMovingViolations implements Comparable<VOMovingViolations> {

	
	//---------------------------------------------------------------------------------------------
	// ATRIBUTOS 
	//---------------------------------------------------------------------------------------------
	private String objectId;
	private String emptyRow;
	private String location;
	private String address_id;	
	private String streetsegid;
	private double xcoord;	
	private double ycoord;
	private String tickettype;
	private int fineamt;	
	private String totalpaid;	
	private String penalty1;
	private String penalty2;
	private String accidentindicator;
	private GregorianCalendar ticketissuedate;
	private String violationcode;
	private String violationdesc;

	//---------------------------------------------------------------------------------------------
	// CONSTRUCTOR
	//---------------------------------------------------------------------------------------------
	public VOMovingViolations (String pObjectId, String pEmptyRow, String pLocation, String pAddress_id, String pStreetsegid,
	double pXcoord, double pYcoord, String pTickettype , int pFineamt, String pTotalpaid, String pPenalty1, String pPenalty2,
	String pAccidentindicator, GregorianCalendar pTicketissuedate , String pViolationcode , String pViolationdesc)
	{
		objectId = pObjectId ;
		emptyRow = pEmptyRow;
		location = pLocation;
		address_id = pAddress_id;	
		streetsegid = pStreetsegid;
		xcoord = pXcoord;	
		ycoord = pYcoord;
		tickettype = pTickettype;
		fineamt = pFineamt;	
		totalpaid = pTotalpaid;	
		penalty1 = pPenalty1;
		penalty2 = pPenalty2;
		accidentindicator = pAccidentindicator;
		ticketissuedate = pTicketissuedate;
		violationcode = pViolationcode;
		violationdesc = pViolationdesc;
	}
	
	//---------------------------------------------------------------------------------------------
	// METODOS
	//---------------------------------------------------------------------------------------------
	
	/**
	 * @return id - Identificador único de la infracción
	 */
	public String getObjectId() {
		return objectId;
	}	
	
	/**
	 * @return location - Dirección en formato de texto.
	 */
	public String getEmptyRow() {
		return emptyRow;
	}
	
	/**
	 * @return location - Dirección en formato de texto.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return identificación de la dirección
	 */
	public String getAddressId()
	{
		return address_id;
	}
	
	/**
	 * @return identificación del segmento de la calle
	 */
	public String getStreetSegId() {
		return streetsegid;
	}
	
	/**
	 * @return Coordenada X donde ocurrió (No corresponde a longitud geográfica)
	 */
	public double getXCoordinate()
	{
		return xcoord;
	}
	
	/**
	 * @return Coordenada X donde ocurrió (No corresponde a longitud geográfica)
	 */
	public double getYCoordinate()
	{
		return ycoord;
	}
	
	/**
	 * @return tipo de ticket
	 */
	public String getTicketType()
	{
		return tickettype;
	}
	
	
	/**
	 * @return Cantidad a pagar por la infracción USD .
	 */
	public int getFineAmt() {
		return fineamt;
	}
	
	/**
	 * @return totalPaid - Cuanto dinero efectivamente pagó el que recibió la infracción en USD.
	 */
	public String getTotalPaid() {
		return totalpaid;
	}
	
	/**
	 * @return Dinero extra que debe pagar el conductor
	 */
	public String getPenalty1() {
		return penalty1;
	}
	
	/**
	 * @return Dinero extra que debe pagar el conductor
	 */
	public String getPenalty2() {
		return penalty2;
	}
	
	/**
	 * @return Si hubo un accidente o no
	 */
	public String getAccidentIndicator() {
		return accidentindicator;
	}
	
	public GregorianCalendar getTicketIssueDate() {
		// TODO Auto-generated method stub
		return ticketissuedate;
	}
	
	public String getViolationCode() {
		// TODO Auto-generated method stub
		return violationcode;
	}

	public String getViolationDescription() {
		// TODO Auto-generated method stub
		return violationdesc;
	}



	@Override
	public int compareTo(VOMovingViolations o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
