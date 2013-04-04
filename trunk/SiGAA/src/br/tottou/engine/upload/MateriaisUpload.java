package br.tottou.engine.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import org.primefaces.event.FileUploadEvent;

public class MateriaisUpload {

	
	public String uploadImg(FileUploadEvent fue, String caminho, String nomeArquivo) throws IOException {
		
	nomeArquivo = nomeArquivo+fue.getFile().getFileName().substring(fue.getFile().getFileName().lastIndexOf("."));
        copiaArq(caminho+nomeArquivo,fue.getFile().getInputstream()); 
       
        return nomeArquivo;
	}
	
	public String uploadSom(FileUploadEvent fue, String caminho, String nomeArquivo) throws IOException {
		
		nomeArquivo = nomeArquivo+fue.getFile().getFileName().substring(fue.getFile().getFileName().lastIndexOf("."));
	        copiaArq(caminho+nomeArquivo,fue.getFile().getInputstream()); 
	       
	        return nomeArquivo;
		}
	
	public String uploadVideo(FileUploadEvent fue, String caminho, String nomeArquivo) throws IOException {
		
		nomeArquivo = nomeArquivo+fue.getFile().getFileName().substring(fue.getFile().getFileName().lastIndexOf("."));
	        copiaArq(caminho+nomeArquivo,fue.getFile().getInputstream()); 
	       
	        return nomeArquivo;
		}
	
	public String uploadArq(FileUploadEvent fue, String caminho, String nomeArquivo) throws IOException {
		
		nomeArquivo = nomeArquivo+fue.getFile().getFileName().substring(fue.getFile().getFileName().lastIndexOf("."));
	        copiaArq(caminho+nomeArquivo,fue.getFile().getInputstream()); 
	       
	        return nomeArquivo;
		}
	
	
	
	
	 private void copiaArq(String arquivo, InputStream in) {
         try {
           
      	
           
              // write the inputStream to a FileOutputStream
              OutputStream out = new FileOutputStream(new File(arquivo));
             
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
  
	
	
	
}
