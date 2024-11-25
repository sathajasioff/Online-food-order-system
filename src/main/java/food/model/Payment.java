package food.model;

import java.time.LocalDate;

public class Payment {
	
	private int id;                
    private String name;           
    private String email;          
    private String cardNumber;    
    private LocalDate expiryDate;  
    private String cvv;            
    private String address;        
    private LocalDate paymentDate;
    
    
    
	public Payment() {
	}



	public Payment(int id, String name, String email, String cardNumber, LocalDate expiryDate, String cvv,
			String address) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
		this.address = address;
		this.paymentDate = paymentDate;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCardNumber() {
		return cardNumber;
	}



	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}



	public LocalDate getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}



	public String getCvv() {
		return cvv;
	}



	public void setCvv(String cvv) {
		this.cvv = cvv;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public LocalDate getPaymentDate() {
		return paymentDate;
	}



	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
    
    
	
}
