package com.fastcar.chiron

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.*
import java.text.SimpleDateFormat
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.sun.jna.Platform;


@SpringBootApplication
class ChironApplication: CommandLineRunner {
    override fun run(vararg args: String?) {
        Thread.currentThread().join()
    }

    fun ChironApplication() {
        log.info("The time is now {}", dateFormat.format(Date()))
    }

    private val log = LoggerFactory.getLogger(ChironApplication::class.java)
    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(fixedRate = 5000)
    fun reportCurrentTime() {
        if (Platform.isMac()) {
            val script = "global frontApp, frontAppName, windowTitle \n" +
            "set windowTitle to \"\" \n" +
            "tell application \"System Events\" \n" +
            "  set frontApp to first application process whose frontmost is true \n" +
            "  set frontAppName to name of frontApp \n" +
            "  tell process frontAppName \n" +
            "    if frontAppName is \"java\" or frontApp is \"JavaAppLauncher\" then \n" +
            "      tell process frontAppName \n" +
            "        (unix id of frontApp) & (name of frontApp) \n" +
            "      end tell \n" +
            "    else if exists window of frontApp then \n" +
            "      try \n" +
            "        tell (1st window whose value of attribute \"AXMain\" is true) \n" +
            "          set windowTitle to (unix id of frontApp) & (name of frontApp) & (value of attribute \"AXTitle\") \n" +
            "        end tell \n" +
            "      on error errmess \n" +
            "        tell process frontAppName \n" +
            "          (unix id of frontApp) & (name of frontApp) \n" +
            "        end tell \n" +
            "      end try \n" +
            "    else \n" +
            "      tell process frontAppName \n" +
            "        (unix id of frontApp) & (name of frontApp) \n" +
            "      end tell \n" +
            "    end if \n" +
            "  end tell \n" +
            "end tell"
            val appleScript = ScriptEngineManager().getEngineByName("AppleScript")
            val result = appleScript?.eval(script)
            println(result)
        }
    }


}

fun main(args: Array<String>) {
    SpringApplication.run(ChironApplication::class.java, *args)
}
