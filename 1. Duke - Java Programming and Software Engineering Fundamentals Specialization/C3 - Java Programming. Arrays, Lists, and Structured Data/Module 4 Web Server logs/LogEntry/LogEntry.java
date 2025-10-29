import java.util.*;

public class LogEntry {
    private String ipAdress;
    private Date accesTime;
    private String request;
    private int statusCode;
    private int bytesReturned;
    
    public LogEntry(String ip,Date time, String req, int status, int bytes){
        ipAdress = ip;
        accesTime = time;
        request = req;
        statusCode = status;
        bytesReturned = bytes;
    }
    
    public String getIpAdress(){
        return ipAdress;
    }
    
    public Date getAccesTime(){
        return accesTime;
    }
    
    public String getRequest(){
        return request;
    }
    
    public int getStatusCode(){
        return statusCode;
    }
    
    public int getBytesReturned(){
        return bytesReturned;
    }
    
    public String toString(){
        return ipAdress + " " + accesTime + " " + request
        + " " + statusCode + " " + bytesReturned;
    }
}