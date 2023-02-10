package es.espublico.ejercicio.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

/**
 * POJO que contiene la información de un registro del CSV Ventas
 * Esta clase contiene anotaciones de OpenCsv para que mapee por nombre cada atributo con la columna del CSV
 * @autor Manuel León
 * 
 * @since 04/02/2023
 *
 */
public class Venta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@CsvBindByName(column = "Order ID")
	private long orderId;
	
	@CsvBindByName(column = "Region")
	private String region;
	
	@CsvBindByName(column = "Country")
	private String country;
	
	@CsvBindByName(column = "Item Type")
	private String itemType;
	
	@CsvBindByName(column = "Sales Channel")
	private String salesChannel;
	
	@CsvBindByName(column = "Order Priority")
	private String orderPriority;
	
	@CsvBindByName(column = "Order Date")
	@CsvDate(value = "MM/dd/yyyy")
	private Date orderDate;
	
	@CsvBindByName(column = "Ship Date")
	@CsvDate(value = "MM/dd/yyyy")
	private Date shipDate;
	
	@CsvBindByName(column = "Units Sold")
	private int unitsSold;
	
	@CsvBindByName(column = "Unit Price")
	private double unitPrice;
	
	@CsvBindByName(column = "Unit Cost")
	private double unitCost;
	
	@CsvBindByName(column = "Total Revenue")
	private double totalRevenue;
	
	@CsvBindByName(column = "Total Cost")
	private double totalCost;
	
	@CsvBindByName(column = "Total Profit")
	private double totalProfit;
	
	//Getters y Setters
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	public String getOrderPriority() {
		return orderPriority;
	}
	public void setOrderPriority(String orderPriority) {
		this.orderPriority = orderPriority;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	public int getUnitsSold() {
		return unitsSold;
	}
	public void setUnitsSold(int unitsSold) {
		this.unitsSold = unitsSold;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}
	
	//Métodos sobrecargados para poder distinguir por el campo order_id y poder ordenar listas
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Venta venta = (Venta) o;
		return this.orderId == venta.getOrderId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.orderId);
	}

}
