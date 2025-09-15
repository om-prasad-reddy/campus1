package com.campus1.admin.serv;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AdminFileStorageService {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminFileStorageService.class);
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    
    public String storeFile(MultipartFile file, String subfolder) throws IOException {
        try {
            // Create base uploads directory if it doesn't exist
            Path baseUploadPath = Paths.get(uploadDir).toAbsolutePath();
            if (!Files.exists(baseUploadPath)) {
                Files.createDirectories(baseUploadPath);
                logger.info("Created base uploads directory: {}", baseUploadPath);
                setDirectoryPermissions(baseUploadPath);
            }
            
            // Create admin-specific subdirectory
            Path adminDirPath = baseUploadPath.resolve(subfolder);
            if (!Files.exists(adminDirPath)) {
                Files.createDirectories(adminDirPath);
                logger.info("Created admin directory: {}", adminDirPath);
                setDirectoryPermissions(adminDirPath);
            }
            
            // Generate unique filename with UUID and timestamp
            String uniqueId = UUID.randomUUID().toString();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = "profile_" + timestamp + "_" + uniqueId + getFileExtension(file.getOriginalFilename());
            Path targetLocation = adminDirPath.resolve(fileName);
            
            // Log the target location
            logger.info("Saving file to: {}", targetLocation);
            
            // Save file with explicit permissions
            Files.copy(file.getInputStream(), targetLocation);
            setFilePermissions(targetLocation);
            
            // Verify file was saved
            if (Files.exists(targetLocation)) {
                long fileSize = Files.size(targetLocation);
                logger.info("File saved successfully. Size: {} bytes", fileSize);
                
                // Return relative path from uploads directory
                return subfolder + "/" + fileName;
            } else {
                logger.error("ERROR: File was not saved at {}", targetLocation);
                throw new IOException("Failed to save file");
            }
        } catch (IOException ex) {
            logger.error("Could not store file {}", file.getOriginalFilename(), ex);
            throw new IOException("Could not store file " + file.getOriginalFilename(), ex);
        }
    }
    
    private void setDirectoryPermissions(Path path) throws IOException {
        try {
            // Set directory permissions to 755 (rwxr-xr-x)
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            
            Files.setPosixFilePermissions(path, perms);
            logger.debug("Set permissions for directory: {}", path);
        } catch (UnsupportedOperationException e) {
            // Non-POSIX system (like Windows), set readable/writable
            path.toFile().setReadable(true, false); // readable for all
            path.toFile().setWritable(true, false); // writable for all
            path.toFile().setExecutable(true, false); // executable for all
            logger.debug("Set permissions for non-POSIX directory: {}", path);
        }
    }
    
    private void setFilePermissions(Path path) throws IOException {
        try {
            // Set file permissions to 644 (rw-r--r--)
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.OTHERS_READ);
            
            Files.setPosixFilePermissions(path, perms);
            logger.debug("Set permissions for file: {}", path);
        } catch (UnsupportedOperationException e) {
            // Non-POSIX system (like Windows), set readable/writable
            path.toFile().setReadable(true, false); // readable for all
            path.toFile().setWritable(true, false); // writable for owner
            logger.debug("Set permissions for non-POSIX file: {}", path);
        }
    }
    
    public void deleteFile(String filePath) throws IOException {
        try {
            Path targetLocation = Paths.get(uploadDir).resolve(filePath).toAbsolutePath();
            logger.info("Attempting to delete file: {}", targetLocation);
            
            if (Files.exists(targetLocation)) {
                Files.delete(targetLocation);
                logger.info("File deleted successfully: {}", targetLocation);
                
                // Try to delete the admin directory if it's empty
                Path parentDir = targetLocation.getParent();
                if (Files.exists(parentDir) && 
                    Files.list(parentDir).count() == 0) {
                    Files.delete(parentDir);
                    logger.info("Deleted empty admin directory: {}", parentDir);
                }
            } else {
                logger.warn("File not found for deletion: {}", targetLocation);
            }
        } catch (IOException ex) {
            logger.error("Could not delete file {}", filePath, ex);
            throw new IOException("Could not delete file " + filePath, ex);
        }
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == 0) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    /**
     * Verify that a file exists at the given path
     */
    public boolean verifyFileExists(String filePath) {
        try {
            Path targetLocation = Paths.get(uploadDir).resolve(filePath).toAbsolutePath();
            boolean exists = Files.exists(targetLocation);
            logger.debug("File exists check for {}: {}", filePath, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking file existence for {}: {}", filePath, e.getMessage());
            return false;
        }
    }
    
    /**
     * Ensure all admin directories exist
     */
    public void ensureAllAdminDirectoriesExist() {
        try {
            Path baseUploadPath = Paths.get(uploadDir).toAbsolutePath();
            if (!Files.exists(baseUploadPath)) {
                Files.createDirectories(baseUploadPath);
                logger.info("Created base uploads directory: {}", baseUploadPath);
            }
            
            // List all admin directories
            File[] adminDirs = baseUploadPath.toFile().listFiles((dir, name) -> name.startsWith("admin-"));
            if (adminDirs != null) {
                logger.info("Found {} admin directories", adminDirs.length);
            }
        } catch (IOException e) {
            logger.error("Error ensuring admin directories exist: {}", e.getMessage());
        }
    }
    
    /**
     * Create admin directory if it doesn't exist
     */
    public void ensureAdminDirectoryExists(Long adminId) {
        try {
            String subfolder = "admin-" + adminId;
            Path baseUploadPath = Paths.get(uploadDir).toAbsolutePath();
            
            if (!Files.exists(baseUploadPath)) {
                Files.createDirectories(baseUploadPath);
                logger.info("Created base uploads directory: {}", baseUploadPath);
            }
            
            Path adminDirPath = baseUploadPath.resolve(subfolder);
            if (!Files.exists(adminDirPath)) {
                Files.createDirectories(adminDirPath);
                logger.info("Created admin directory for admin {}: {}", adminId, adminDirPath);
                setDirectoryPermissions(adminDirPath);
            }
        } catch (IOException e) {
            logger.error("Error creating admin directory for admin {}: {}", adminId, e.getMessage());
        }
    }
}