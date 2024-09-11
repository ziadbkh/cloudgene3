package cloudgene.mapred.jobs.engine.handler;

import java.io.IOException;
import java.util.Map;

public class JobErrorHandlerFactory {

    public static IJobErrorHandler createByMap(Map<String, String> map) throws IOException {
        if (!map.containsKey("type")) {
            throw new IOException("Job Error Handler not created. property `type` not found.");
        }
        if (!map.containsKey("name")) {
            throw new IOException("Job Error Handler not created. property `name` not found.");
        }
        String type = map.get("type");
        String name = map.get("name");
        /*if (type.equalsIgnoreCase("slurm")) {
            return new SlurmHandler(name);
        }*/
        if (type.equalsIgnoreCase("command")) {
            String command = map.get("command");
            if (command == null) {
                throw new IOException("Job Error Handler not created. property `command`  not found.");
            }
            return new CommandHandler(name, command);
        }

        throw new IOException("Job Error Handler not created. Unknown type.");

    }

}
