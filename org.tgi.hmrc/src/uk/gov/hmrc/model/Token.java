package uk.gov.hmrc.model;

public class Token {

    private final String accessToken;
    private final String refreshToken;

    public Token(String accessToken, String refreshToken) {
    	System.out.println("Token");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
