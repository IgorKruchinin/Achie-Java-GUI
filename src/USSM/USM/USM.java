package USSM.USM;
//import com.sun.nio.zipfs.ZipFileSystem;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class USM {
    private String name_;
    private String program_name_ = "";
    //private Map<String, IntSection> isecs_;
    //private Map<String, StringSection> ssecs_;
    private SortedMap<String, Section> secs_;
    private List<Integer> formats;
    private boolean is_opened;
    public USM(final String name) {
        name_ = name;
        Path path = Paths.get("profiles", File.separator,  name_ + ".uto");
        //isecs_ = new HashMap<>();
        //ssecs_ = new HashMap<>();
        secs_ = new TreeMap<>();
        formats = new Vector<>();
        try {
            is_opened = true;
            for (String s: Files.readAllLines(path, StandardCharsets.UTF_8)) {
                if (s.charAt(0) == 'i') {
                    Section auto = new IntSection("auto");
                    auto.parse(s);
                    secs_.put(auto.get_name(), auto);
                    formats.add(0);
                } else if (s.charAt(0) == 's') {
                    Section auto = new StringSection("auto");
                    auto.parse(s);
                    secs_.put(auto.get_name(), auto);
                    formats.add(1);
                }
            }
        } catch (IOException | USMSectionException e) {
            is_opened = false;
        }
        if (!is_opened) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                Path prof_list = Paths.get("profiles", File.separator, "profiles_list.txt");
                Files.createFile(prof_list);
                Files.write(prof_list, name_.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), "\n".getBytes(), StandardOpenOption.APPEND);

            } catch (IOException e) {
                System.exit(1);
            }
        }
    }
    public USM(String name, int flag) {
        if (flag == 1) {
            try {
                name_ = name;
                //isecs_ = new HashMap<>();
                //ssecs_ = new HashMap<>();
                secs_ = new TreeMap<>();
                formats = new Vector<>();
                Path path = Paths.get("profiles", File.separator, name_ + ".uto");
                Files.createFile(path);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), name_.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), "\n".getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ignored) {}
        }
    }
    public USM(final String name, final String program_name) {
        name_ = name;
        program_name_ = program_name;
        Path path = Paths.get("profiles", File.separator,  name_ + ".uto");
        //isecs_ = new HashMap<>();
        //ssecs_ = new HashMap<>();
        secs_ = new TreeMap<>();
        formats = new Vector<>();
        try {
            is_opened = true;
            for (String s: Files.readAllLines(path, StandardCharsets.UTF_8)) {
                if (s.charAt(0) == 'i') {
                    Section auto = new IntSection("auto");
                    auto.parse(s);
                    secs_.put(auto.get_name(), auto);
                    formats.add(0);
                } else if (s.charAt(0) == 's') {
                    Section auto = new StringSection("auto");
                    auto.parse(s);
                    secs_.put(auto.get_name(), auto);
                    formats.add(1);
                }
            }
        } catch (IOException | USMSectionException e) {
            is_opened = false;
        }
        if (!is_opened) {
            try {
                Files.createFile(path);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), (name_ + ":" + program_name).getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), "\n".getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.exit(1);
            }
        }
    }
    public USM(String name, String program_name, int flag) {
        if (flag == 1) {
            try {
                name_ = name;
                program_name_ = program_name;
                //isecs_ = new HashMap<>();
                //ssecs_ = new HashMap<>();
                secs_ = new TreeMap<>();
                formats = new Vector<>();
                Path path = Paths.get("profiles", File.separator, name_ + ".uto");
                Files.createFile(path);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), (name_ + ":" + program_name).getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("profiles", File.separator, "profiles_list.txt"), "\n".getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ignored) {}
        }
    }
    public final String get_name() {
        return name_;
    }
    public void to_file() {
        Path path = Paths.get("profiles", File.separator, name_ + ".uto");
        try (final OutputStream outputStream = Files.newOutputStream(path)) {
            StringBuilder text_buf = new StringBuilder();
            for (Map.Entry<String, Section> entry: secs_.entrySet()) {
                if (entry.getValue() instanceof StringSection) {
                    text_buf.append("s<").append(entry.getKey()).append(">");
                    for (String obj : ((StringSection)entry.getValue()).getObjects_()) {
                        text_buf.append(obj).append("|<\\e>");
                    }
                } else if (entry.getValue() instanceof IntSection) {
                    text_buf.append("i<").append(entry.getKey()).append(">");
                    for (Long obj: ((IntSection)entry.getValue()).getObjects_()) {
                        text_buf.append(obj).append("|<\\e>");
                    }
                }
                text_buf.append("\n");
            }
            outputStream.write(text_buf.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void to_archive(final String filename, final String typename) throws IOException {

        Path path = Paths.get("profiles", File.separator, name_ + ".uto");
        try (final OutputStream outputStream = Files.newOutputStream(path)) {
            StringBuilder text_buf = new StringBuilder();
            for (Map.Entry<String, Section> entry: secs_.entrySet()) {
                if (entry.getValue() instanceof StringSection) {
                    text_buf.append("s<").append(entry.getKey()).append(">");
                    for (String obj : ((StringSection)entry.getValue()).getObjects_()) {
                        text_buf.append(obj).append("|<\\e>");
                    }
                } else if (entry.getValue() instanceof IntSection) {
                    text_buf.append("i<").append(entry.getKey()).append(">");
                    for (Long obj: ((IntSection)entry.getValue()).getObjects_()) {
                        text_buf.append(obj).append("|<\\e>");
                    }
                }
                text_buf.append("\n");
            }
            outputStream.write(text_buf.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        Path zipFilePath = Paths.get(filename + "." + typename);

        URI uri = URI.create("jar:file:" + zipFilePath.toAbsolutePath().toString());

        try (FileSystem zipFs = FileSystems.newFileSystem(uri, env)) {
            Path zipPath = zipFs.getPath(path.getFileName().toString());
            Path zipRes = zipFs.getPath("res", File.separator, name_);
            Files.copy(path, zipPath, StandardCopyOption.REPLACE_EXISTING);
            Files.createDirectory(zipRes);
        } catch (FileAlreadyExistsException ignore) {

        }
    }

    public File to_prof_archive(File archive) {
        try {
            BufferedInputStream o = null;
            // File archive = new File(context.getExternalFilesDir(null) + File.separator + name_ + ".profpack");
            FileOutputStream to =  new FileOutputStream(archive);
            ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(to));
            byte[] data = new byte[512];

            FileInputStream utoInput = new FileInputStream(new File("profiles" + File.separator + name_ + ".uto"));
            o = new BufferedInputStream(utoInput, 512);
            ZipEntry uosEntry = new ZipEntry(name_ + ".uto");
            output.putNextEntry(uosEntry);
            int k = 0;
            while((k = o.read(data, 0, 512)) != -1) {
                output.write(data, 0, k);
            }
            o.close();
            File resDir = new File("profiles" + File.separator + "res" + File.separator + name_);
            if (resDir.listFiles() != null) {
                for (File inputFile : resDir.listFiles()) {
                    if (inputFile.exists() && inputFile.isFile()) {
                        FileInputStream inputStream = new FileInputStream(inputFile);
                        o = new BufferedInputStream(inputStream, 512);
                        ZipEntry entry = new ZipEntry("res" + File.separator + name_ + File.separator + inputFile.getName());
                        output.putNextEntry(entry);
                        int count = 0;
                        while ((count = o.read(data, 0, 512)) != -1) {
                            output.write(data, 0, count);
                        }
                        o.close();
                    }
                }
            }
            output.close();
            return archive;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public File to_one_archive(final File archive, int index, final String... resNames) throws IOException {
        try {

            Path path = Paths.get("profiles", File.separator, name_ + ".uos");
            try (final OutputStream outputStream = Files.newOutputStream(path)) {
                StringBuilder text_buf = new StringBuilder();
                for (Map.Entry<String, Section> entry: secs_.entrySet()) {
                    if (entry.getValue() instanceof StringSection) {
                        text_buf.append(entry.getKey()).append(":");
                        text_buf.append(((StringSection)entry.getValue()).get(index));
                    } else if (entry.getValue() instanceof IntSection) {
                        text_buf.append(entry.getKey()).append(":");
                        text_buf.append(String.valueOf(((IntSection)entry.getValue()).get(index)));
                    }
                    text_buf.append("\n");
                }
                outputStream.write(text_buf.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }



            BufferedInputStream o = null;
            //File archive = new File(context.getExternalFilesDir(null) + File.separator + filename + "." + typename);
            FileOutputStream to =  new FileOutputStream(archive);
            ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(to));
            byte[] data = new byte[512];

            FileInputStream uosInput = new FileInputStream(path.toString());
            o = new BufferedInputStream(uosInput, 512);
            ZipEntry uosEntry = new ZipEntry(path.getFileName().toString());
            output.putNextEntry(uosEntry);
            int k = 0;
            while((k = o.read(data, 0, 512)) != -1) {
                output.write(data, 0, k);
            }
            o.close();
            for (int i = 0; i < resNames.length; ++i) {
                System.out.println(resNames[i]);
                File inputFile = new File("profiles" + File.separator + "res"+ File.separator + name_ + File.separator + resNames[i]);
                if (inputFile.exists() && inputFile.isFile()) {
                    FileInputStream inputStream = new FileInputStream(inputFile);
                    o = new BufferedInputStream(inputStream, 512);
                    ZipEntry entry = new ZipEntry("res" + File.separator + resNames[i]);
                    output.putNextEntry(entry);
                    int count = 0;
                    while ((count = o.read(data, 0, 512)) != -1) {
                        output.write(data, 0, count);
                    }
                    o.close();
                }
            }
            output.close();
            return archive;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void from_profile_archive(ZipFile archive) throws IOException {
        Enumeration<? extends ZipEntry> entries = archive.entries();
        File profilesDir = new File("profiles");
        File resDir = new File(profilesDir, "res");
        resDir.mkdirs();
        profilesDir.mkdirs();
        Vector<String> names = new Vector<String>();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                File dir = new File("profiles" + File.separator + entry.getName());
                dir.mkdirs();
            } else {
                int index  = entry.getName().lastIndexOf('.');
                if (index >= 0 && entry.getName().substring(index + 1).equals("uto")) {
                    String name = entry.getName().substring(0, entry.getName().lastIndexOf('.'));
                    File res = new File(resDir, name);
                    res.mkdirs();
                    names.add(name);
                }
                InputStream inputStream = archive.getInputStream(entry);
                File file = new File(profilesDir, entry.getName());
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[512];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
            }

        }
        File profiles_list = new File("profiles" + File.separator +"profiles_list.txt");
        if (!profiles_list.exists()) {
            profiles_list.createNewFile();
        }
        for (String name : names) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(profiles_list, true)));
            bufferedWriter.write(name + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    public IntSection geti(final String name) {
        return (IntSection)secs_.get(name);
    }
    public StringSection gets(final String name) {
        return (StringSection)secs_.get(name);
    }
    public Section get(String name) {
        return secs_.get(name);
    }
    public final Collection<Section> getAll() {
        return secs_.values();
    }
    public void create_isec(String name) {
        secs_.put(name, new IntSection(name));
        formats.add(0);
    }
    public void create_ssec(String name) {
        secs_.put(name, new StringSection(name));
        formats.add(1);
    }
    public final List<Integer> get_formats() {
        return formats;
    }
    public final String get_program_name() {
        return program_name_;
    }
    public int size() {
        return secs_.size();
    }
    public final boolean opened() {
        return is_opened;
    }
    public static List<USM> get_profiles() {
        List<USM> profiles = new Vector<>();
        Path path = Paths.get("profiles", File.separator,"profiles_list.txt");
        try {
            for (String s : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String[] s1 = s.split(":");
                profiles.add(new USM(s1[0]));
            }
        } catch (IOException e) {
            try {
                Files.createFile(path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return profiles;
    }
    public static List<USM> get_profiles(final String program_name) {
        List<USM> profiles = new Vector<>();
        Path path = Paths.get("profiles", File.separator,"profiles_list.txt");
        try {
            for (String s : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String[] s1 = s.split(":");
                if (s1.length == 1 || s1[1].equals(program_name)) {
                    profiles.add(new USM(s1[0]));
                }
            }
        } catch (IOException e) {
            try {
                Files.createFile(path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return profiles;
    }
}

class Arithmetics {
    public static int computeExpression(String expression) {
        List<StringBuilder> sb = new Vector<>();
        List<Integer> op = new Vector<>();
        int k = -1;
        boolean crStrBld= true;
        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (c != ' ') {
                if (c == '+') {
                    op.add(0);
                    crStrBld = true;
                }  else if (c == '-') {
                    op.add(1);
                    crStrBld = true;
                }  else if (c == '*') {
                    op.add(2);
                    crStrBld = true;
                }  else if (c == '/') {
                    op.add(3);
                    crStrBld = true;
                } else {
                    if (crStrBld) {
                        sb.add(new StringBuilder());
                        ++k;
                        crStrBld = false;
                    } else {
                        sb.get(k).append(c);
                    }
                }
            }
        }
        int result = Integer.parseInt(sb.get(0).toString());
        for (int i = 1, j = 0; i < sb.size() && j < sb.size() - 1; ++i, ++j) {
            switch (op.get(j)) {
                case 0:
                    result += Integer.parseInt(sb.get(i).toString());
                    break;
                case 1:
                    result -= Integer.parseInt(sb.get(i).toString());
                    break;
                case 2:
                    result *= Integer.parseInt(sb.get(i).toString());
                    break;
                case 3:
                    result /= Integer.parseInt(sb.get(i).toString());
                    break;
            }
        }
        return result;
    }
}