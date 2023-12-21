package function;

import java.io.File;

public class ProjectLocationDetector {

    public static String getProjectLocation() {
        String classPath = ProjectLocationDetector.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String projectPath = new File(classPath).getParentFile().getParentFile().getPath();
        return projectPath + File.separator; // Add separator here
    }
}
