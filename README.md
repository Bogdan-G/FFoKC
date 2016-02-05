# FF of KCauldron
### FF of KCauldron - assembly modifications

## Info Fork
It is a fork of a fork, which is the preservation of a few minor changes made by me, as well as other changes to other people.
Some edits test for preserving the history of what they were doing. Part specific to the assembly BMPL.
Changes can be controversial.
Changes underway in the direction of my modpack.

## I used to compile
* Win 7 x64
* Zulu JDK 7u91 x64
* Gradle 2.0
* git-scm 2.7.0 x64

## Building FF of KCauldron
* Checkout project
  `git clone https://github.com/Bogdan-G/KCauldron.git`
* Init submodules
  * Since this project is merger of two other we need both.
  `git submodule update --init --recursive`
* Setup initial workspace
  `gradle setupCauldron`
* Setup bundle
  `gradle installBundle`
* Build jar
  `gradle jar`

## Updating sources
If you're once checkout source - you not need to do it again
* Update sources
  * `git pull origin master`
* Reapply patches & build binaries
  * `gradle clean setupCauldron jar`

## License project
it is not an ordinary license =)

<a href="http://www.wtfpl.net/" target="_blank">![WTFPL_dowl][License_img]</a>

[License_img]: http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-4.png
[WTFPL_dowl]: http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-4.png

## Credits
Contributors projects: MC, MCP, Forge, Bukkit, Spigot, PaperSpigot, Cauldron, KCauldron, Thermos, NEID, etc

## Special Thanks
Prototik, Robotia, Fewizz, SvEgiiVEteR, etc

P.S. In `Credits` & `Special Thanks` - roughly grouped into categories, not the level of merit.(примерно сгруппированы по категориям, не уровень заслуг.)
