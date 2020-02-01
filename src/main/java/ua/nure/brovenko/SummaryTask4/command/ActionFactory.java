package ua.nure.brovenko.SummaryTask4.command;


import ua.nure.brovenko.SummaryTask4.command.admin.*;
import ua.nure.brovenko.SummaryTask4.command.entrant.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Class that contains main command parameters
 * @author Ivan Brovenko
 */
public class ActionFactory{

    /**
     * Name of main command
     */
    public static final String COMMAND_PARAMETER = "c";

    /**
     * Container for command parameters
     */
    private HashMap<String,ActionCommand> commands = new HashMap<>();
    {
        commands.put("login",new LoginCommand());
        commands.put("logout",new LogoutCommand());
        commands.put("reg",new SignUpCommand());
        commands.put("acc",new AccountCommand());
        commands.put("regAtt",new RegAtestatCommand());
        commands.put("appl",new FinalRegistrantionCommand());
        commands.put("zno",new SaveZnoCommand());
        commands.put("cong",new CongratsCommand());
        commands.put("watchZno",new WatchSavedApplicationCommand());
        commands.put("watchInfo",new WatchInformationCommand());
        commands.put("manage",new ManagementCommand());
        commands.put("depManage",new DepartmentManagementCommand());
        commands.put("del",new DeleteDepartmentCommand());
        commands.put("add",new AddDepartmentCommand());
        commands.put("edit",new EditDepartmentCommand());
        commands.put("update",new UpdateDepartmentCommand());
        commands.put("studManage",new EntrantManagementCommand());
        commands.put("add2adm",new AddToAdmissionCommand());
        commands.put("block",new BlockEntrantCommand());
        commands.put("entDep",new EntrantDepartmentsCommand());
        commands.put("entInf",new EntrantInfoCommand());
        commands.put("finAdmiss",new FinalAdmissionCommand());
        commands.put("send",new SendEmailCommand());
        commands.put("emailReg",new EmailSignUpCommand());
        commands.put("contact",new ContactAdminCommand());
        commands.put("znoInf",new EntrantZnoInfoCommand());
        commands.put("blackList",new BlackListCommand());
        commands.put("unblock",new UnblockEntrantCommand());
        commands.put("upload",new FileUploadingCommand());
        commands.put("forg",new ForgotPasswordCommand());
        commands.put("best5",new Best5StudentsCommand());
    }

    /**
     * Method for finding command from request
     * @param request request to read command from
     * @return command parameter name
     */
    public ActionCommand getCommand(HttpServletRequest request){
        String action = request.getParameter(COMMAND_PARAMETER);

        return getCommand(action);
    }

    /**
     * Method for finding command by name
     * @param action command name
     * @return command parameter from Map
     */
    public ActionCommand getCommand(String action){
        ActionCommand command = commands.get(action);

        if(command == null){
            return new EmptyCommand();
        }

        return command;
    }
}
