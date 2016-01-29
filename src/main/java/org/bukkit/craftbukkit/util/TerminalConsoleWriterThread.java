package org.bukkit.craftbukkit.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.console.ConsoleReader;
import com.mojang.util.QueueLogAppender;
import net.minecraft.server.MinecraftServer; // Cauldron

public class TerminalConsoleWriterThread implements Runnable {
    final private ConsoleReader reader;
    final private OutputStream output;

    public TerminalConsoleWriterThread(OutputStream output, ConsoleReader reader) {
        this.output = output;
        this.reader = reader;
    }

    public void run() {
        String message;

        // Using name from log4j config in vanilla jar
        while (true) {
            message = QueueLogAppender.getNextLogEvent("TerminalConsole");
            if (message == null) {
                continue;
            }

            try {
                if (MinecraftServer.useJline) { // Cauldron
                    reader.print(String.valueOf(ConsoleReader.RESET_LINE));
                    reader.flush();
                    output.write(message.getBytes());
                    output.flush();

                    try {
                        reader.drawLine();
                    } catch (Throwable ex) {
                        reader.getCursorBuffer().clear();
                    }
                    reader.flush();
                } else {
                    output.write(message.getBytes());
                    output.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(TerminalConsoleWriterThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
