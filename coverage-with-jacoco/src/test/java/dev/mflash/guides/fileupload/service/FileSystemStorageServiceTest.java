package dev.mflash.guides.fileupload.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.mflash.guides.fileupload.configuration.StorageProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Random;

public class FileSystemStorageServiceTest {

  private StorageProperties properties = new StorageProperties();
  private FileSystemStorageService service;
  private final String rootDir = "src/test/resources/files/" + Math.abs(new Random().nextLong());

  public @Before void init() {
    properties.setLocation(rootDir);
    service = new FileSystemStorageService(properties);
    service.init();
  }

  public @After void cleanUp() {
    service.deleteAll();
  }

  public @Test void loadNonExistent() {
    assertThat(service.load("foo.txt")).doesNotExist();
  }

  public @Test void saveAndLoad() {
    service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
        "Hello World".getBytes()));
    assertThat(service.load("foo.txt")).exists();
  }

  @Test(expected = StorageException.class)
  public void saveNotPermitted() {
    service.store(new MockMultipartFile("foo", "../foo.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
  }

  public @Test void savePermitted() {
    service.store(new MockMultipartFile("foo", "bar/../foo.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
  }
}