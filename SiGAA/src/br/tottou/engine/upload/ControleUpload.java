package br.tottou.engine.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;



import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;


 

public class ControleUpload {	
	
		
	

	public String upload(FileUploadEvent event, String caminho, long id, String foto) {     
    	    
    	
        FacesMessage msg = new FacesMessage("Foto", event.getFile().getFileName() + " foi enviada!");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        
        //   
        
        String nomeArq = id+event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf("."));
      
      
        
        try {        	
        	
            copyFile(nomeArq, event.getFile().getInputstream(), caminho);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
      nomeArq= nomeArq.toLowerCase();       
      
        return nomeArq;
    }  

    public void copyFile(String fileName, InputStream in, String caminho) {
           try {
             
        	
             
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(caminho+fileName.toLowerCase()));
               
                int read = 0;
                byte[] bytes = new byte[1024];
             
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
             
                in.close();
                out.flush();
                out.close();
             
                System.out.println("Novo arquivo criado!");  
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
    
    
    ///////////////  Upload de foto via camera ///////////////////
    
    
  
     
  
        
      
    public String oncapture(CaptureEvent captureEvent, String caminho, long id, String foto) {  
              
                
        byte[] data = captureEvent.getData();  
        
      
        
        String nomeArq = id+".png";
        
        if (("/images/fotos/"+nomeArq).equals(foto)) {
        	nomeArq="cam"+nomeArq;
        }
           
        
        
        FileImageOutputStream imageOutput;  
        try {  
            imageOutput = new FileImageOutputStream(new File(caminho+nomeArq.toLowerCase()));  
            imageOutput.write(data, 0, data.length);  
            imageOutput.close();  
        }  
        catch(Exception e) {  
            throw new FacesException("Ocorreu um erro na captura da imagem.");  
        }  
        return nomeArq;
    } 
    
    
    
}
