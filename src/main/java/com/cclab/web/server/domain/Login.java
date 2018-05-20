package com.cclab.web.server.domain;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Login {

    @Id
	private String account;
	private String password;
	private String email;
	private int level;
	
    public Login(String account, String password, String email, int level)
            throws ParseException {
        this.account = account;
        this.password = password;
        this.email = email;
        this.level = level;

    }
    public Login(Login l) {
    	this.account = l.account;
        this.password = l.password;
        this.email = l.email;
        this.level = l.level;

    }

	public Login() {
		
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String toString() {
        StringBuilder value = new StringBuilder("MapEntry(");
        value.append("Account: ");
        value.append(account);
        value.append(", password: ");
        value.append(password);
        value.append(", email: ");
        value.append(email);
        value.append(", level: ");
        value.append(level);
        
        value.append(")");
        return value.toString();
    }
}
