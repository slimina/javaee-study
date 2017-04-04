package cn.slimsmart.jenkins.plugin.helloword;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * <p>
 * 当用户配置项目并启用此构建器时{@link DescriptorImpl#newInstance(StaplerRequest)}会被调用创建一个{@link HelloWorldBuilder} 实例
 * 创建实例通过使用持久化到项目配置XML XStream，所以这样可以使用实例字段（如{@link #name}）记住配置。
 * 执行构建时，{@link #perform}方法将被调用。
 * </p>
 */
public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String name;

    // config.jelly中的textbox字段必须与“DataBoundConstructor”中的参数名匹配
    @DataBoundConstructor
    public HelloWorldBuilder(String name) {
        this.name = name;
    }

    /**
     * 我们将在{@code config.jelly}中使用它。
     */
    public String getName() {
        return name;
    }

    /**
     * 执行构建时，perform方法将被调用
     * Build参数是描述了当前任务的一次构建，通过它可以访问到一些比较重要的模型对象如：project当前项目的对象、workspace构建的工作空间、Result当前构建步骤的结果。
     * Launcher参数用于启动构建。
     * BuildListener该接口用于检查构建过程的状态（开始、失败、成功..），通过它可以在构建过程中发送一些控制台信息给jenkins。
     * perform方法的返回值告诉jenkins当前步骤是否成功，如果失败了jenkins将放弃后续的步骤。
     */
    @Override
    public void perform(Run<?,?> build, FilePath workspace, Launcher launcher, TaskListener listener) {
        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.

        // This also shows how you can consult the global configuration of the builder
        if (getDescriptor().getUseFrench())
            listener.getLogger().println("Bonjour, "+name+"!");
        else{
            listener.getLogger().println("Hello, "+name+"!");
        }
        listener.getLogger().println("workspace="+workspace);
        listener.getLogger().println("number="+build.getNumber());
        listener.getLogger().println("url="+build.getUrl());
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    //插件描述类
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    /**
     * Descriptor for {@link HelloWorldBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See {@code src/main/resources/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly}
     * for the actual HTML fragment for the configuration screen.
     * 用于配置屏幕的实际HTML片段
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
    	/**
         * 要保留全局配置信息，只需将其存储在一个字段中并调用save
         * 如果您不希望字段持久化，请使用{@code transient}。
         * global.jelly中checkbox的useFrench一至，全局配置
         */
        private boolean useFrench;

        /**
         * In order为了加载持久化的全局配置，你必须在构造函数中调用load（） 
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         * 执行表单字段“名称”的即时验证。
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         *      <p>
         *      Note that returning {@link FormValidation#error(String)} does not
         *      prevent the form from being saved. It just means that a message
         *      will be displayed to the user. 
         */
        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a name");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the name too short?");
            return FormValidation.ok();
        }

        // 表示此构建器是否可用于各种项目类型
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Say hello world";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            useFrench = formData.getBoolean("useFrench");
            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req,formData);
        }

        /**
         * This method returns true if the global configuration says we should speak French.
         *
         * The method name is bit awkward because global.jelly calls this method to determine
         * the initial state of the checkbox by the naming convention.
         */
        public boolean getUseFrench() {
            return useFrench;
        }
    }
}

