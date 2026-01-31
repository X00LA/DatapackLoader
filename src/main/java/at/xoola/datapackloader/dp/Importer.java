package at.xoola.datapackloader.dp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

import at.xoola.datapackloader.Main;
import at.xoola.datapackloader.util.Copier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Importer {

    private final Logger logger;
    private final Main main;
    private final String separator;

    public void importUrl(String datapacksFolderPath, URL url) throws IOException, NullPointerException {
        String packZipPath = datapacksFolderPath + separator + FilenameUtils.getName(url.getPath());
        File packZip = new File(packZipPath);
        if (!packZip.exists()) {
            Copier.copy(new BufferedInputStream(url.openStream()), packZipPath);
        }
        new Finder(logger, main, separator).fileWalk(datapacksFolderPath, packZip, true);
    }
}