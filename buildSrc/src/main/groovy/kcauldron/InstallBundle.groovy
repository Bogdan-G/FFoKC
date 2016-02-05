package kcauldron

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.*

class InstallBundle extends DefaultTask {
    @InputFile
    def File serverJar

    @InputFiles
    def ConfigurableFileCollection bootstrapClasspath

    @Input
    def String bootstrapMain

    InstallBundle() {
        bootstrapClasspath = project.files()
    }

    def bootstrapClasspath(Object... args) {
        bootstrapClasspath.from args
    }

    @OutputDirectory
    def File getInstallLocation() {
        new File(project.buildDir, 'bundle')
    }

    @TaskAction
    def install() {
        installLocation.deleteDir()
        installLocation.mkdirs()
        new File(installLocation, "README.txt").withWriter {
            def String jarPath = 'bin/' << (project.group as String).replace('.', File.separator) << File.separator << project.name << File.separator << project.version << File.separator << project.name << '-' << project.version << '.jar'

            it << '''#FF of KCauldron

Starter file: KCauldron-1.7.10-N.testNN-server.bat

#github repo: https://github.com/Bogdan-G/KCauldron

#Credits
Contributors projects: MC, MCP, Forge, Bukkit, Spigot, PaperSpigot, Cauldron, KCauldron, Thermos, NEID, etc

#Special Thanks
Prototik, Robotia, Fewizz, SvEgiiVEteR, etc

P.S. In `Credits` & `Special Thanks` - roughly grouped into categories, not the level of merit.(примерно сгруппированы по категориям, не уровень заслуг.)
'''
        }
        def cp = bootstrapClasspath
        for (int i = 0; i < 3; i++) {
            def result = project.javaexec { it ->
                workingDir installLocation
                classpath cp
                main bootstrapMain
                args '--serverDir', installLocation.canonicalPath,
                        '--installServer', serverJar.canonicalFile
            }
            if (result.exitValue == 0) return
        }
        throw new GradleException("Failed to install bundle into ${installLocation}")
    }

    private static final class NopOutputStream extends OutputStream {
        @Override
        void write(byte[] b, int off, int len) throws IOException {
        }

        @Override
        void write(byte[] b) throws IOException {
        }

        @Override
        void write(int b) throws IOException {
        }
    }
}
