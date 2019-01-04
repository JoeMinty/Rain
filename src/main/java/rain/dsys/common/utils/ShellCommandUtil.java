package rain.dsys.common.utils;

import java.io.*;
import java.util.Map;

/**
 * shell 工具类
 */
public class ShellCommandUtil {


    public static Result runCommand(String[] args, Map<String, String> vars, InteractiveHandler interactiveHandler)
            throws IOException, InterruptedException {

        String[] processArgs;
        processArgs = args;

        ProcessBuilder builder = new ProcessBuilder(processArgs);

        if (vars != null) {
            Map<String, String> env = builder.environment();
            env.putAll(vars);
        }

        Process process = builder.start();


        // If an interactiveHandler is supplied ask it for responses to queries from the command
        // using the InputStream and OutputStream retrieved from the Process object. Use the remainder
        // of the data from the InputStream as the data for stdout.
        InputStream inputStream = process.getInputStream();
        if (interactiveHandler != null) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            interactiveHandler.start();

            try {
                while (!interactiveHandler.done()) {
                    StringBuilder query = new StringBuilder();

                    while (reader.ready()) {
                        query.append((char) reader.read());
                    }

                    String response = interactiveHandler.getResponse(query.toString());

                    if (response != null) {
                        writer.write(response);
                        writer.newLine();
                        writer.flush();
                    }
                }
            } catch (IOException ex){
                // ignore exception as command possibly can be finished before writer.flush() or writer.write() called
            } finally {
                writer.close();
            }
        }

        //TODO: not sure whether output buffering will work properly
        // if command output is too intensive
        process.waitFor();
        String stdout = streamToString(inputStream);
        String stderr = streamToString(process.getErrorStream());
        int exitCode = process.exitValue();
        return new Result(exitCode, stdout, stderr);
    }

    public static Result runCommand(String[] args, Map<String, String> vars)
            throws IOException, InterruptedException {
        return runCommand(args, vars, null);
    }

    public static Result runCommand(String[] args) throws IOException,
            InterruptedException {
        return runCommand(args, null);
    }

    private static String streamToString(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static class Result {

        public  Result(int exitCode, String stdout, String stderr) {
            this.exitCode = exitCode;
            this.stdout = stdout;
            this.stderr = stderr;
        }

        private final int exitCode;
        private final String stdout;
        private final String stderr;

        public int getExitCode() {
            return exitCode;
        }

        public String getStdout() {
            return stdout;
        }

        public String getStderr() {
            return stderr;
        }

        public boolean isSuccessful() {
            return exitCode == 0;
        }
    }

    public interface InteractiveHandler {

        boolean done();

        String getResponse(String query);

        void start();
    }

    public static void main(String [] args) throws InterruptedException , IOException {
        String [] params = new String[] {"date"};
        Result result = ShellCommandUtil.runCommand(params);
        System.out.println(result.getExitCode());
    }
}
