package tn.esprit.workspace_workflow.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.workspace_workflow.interfaces.IFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements IFile {

    @Value("${file.path}")
    private String filePath;

    @Override
    public String save(MultipartFile file) {

        String dir = System.getProperty ( "user.dir" ) + "/" + filePath;
        File directory = new File ( dir );

        if (!directory.exists ()) {
            directory.mkdirs ();
        }


        File targetFile = new File ( dir + "/" + file.getOriginalFilename () );
        try {

            file.transferTo ( targetFile );
        } catch (IOException e) {
            throw new RuntimeException ( "Failed to store file " + file.getOriginalFilename (), e );
        }

        return file.getOriginalFilename ();
    }

    @Override
    public Resource getFile(String fileName) {
        // Define the path to the file
        String dir = System.getProperty ( "user.dir" ) + "/" + filePath + "/" + fileName;
        Path path = Paths.get ( dir );

        try {

            Resource resource = new UrlResource ( path.toUri () );
            if (resource.exists () || resource.isReadable ()) {
                return resource;
            } else {
                throw new RuntimeException ( "Could not read file: " + fileName );
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException ( "Error: " + e.getMessage () );
        }
    }
}