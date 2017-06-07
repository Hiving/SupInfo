package Supinfo.site.beans;

import java.util.UUID;

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String sip_id;
    private String sip_password;
    private String statut;

    public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

	public String getSip_id() {
		return sip_id;
	}

	public void setSip_id(String sip_id) {
		this.sip_id = sip_id;
	}

	public String getSip_password() {
		return sip_password;
	}

	public void setSip_password(String sip_password) {
		this.sip_password = sip_password;
	}
	
	public void generateSipCredentials() {
		sip_id = UUID.randomUUID().toString();
		sip_password = UUID.randomUUID().toString();
	}
}
