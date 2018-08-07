package test.java;

import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base64Test {
	
    @Test
    public void encodeAndDecode(){
        String s = "Hello World";
        
        String encoded = Base64.getEncoder().encodeToString(s.getBytes());
        
        System.out.println(encoded);
        
        byte[] decoded = Base64.getDecoder().decode(encoded);
        
        System.out.println(decoded);
        System.out.println(new String(decoded));
        
        Assertions.assertEquals(s, new String(decoded));
    }
}
