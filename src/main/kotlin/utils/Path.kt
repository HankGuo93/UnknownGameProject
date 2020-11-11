package utils

object Path {
    override fun toString(): String = ""

    object Imgs {
        override fun toString(): String = "$Path/imgs"

        object Actors {
            override fun toString(): String = "$Imgs/actors"
            val ACTOR1 = "$this/Actor1.png"
            val supplement = "$this/button_supplement.png"
        }

        object Objs {
            override fun toString(): String = "$Imgs/objs"

            val box = "$this/box.png"
            val coin = "$this/Coin.png"
            val item = "$this/item.png"
            val border = "$this/border.png"
            val border1 = "$this/border-1.png"
            val border2 = "$this/border2.png"
            val shield = "$this/shield.png"
            val headshot = "$this/headshot.png"
            val postion = "$this/postion.png"
            val scroll = "$this/scroll.png"
            val cloud = "$this/Cloud.png"
            val attackClick = "$this/attack_click.png"
            val attackUnclick = "$this/attack_unclick.png"
            val defenceClick = "$this/defence_click.png"
            val defenceUnclick = "$this/defence_unclick.png"
            val lifeClick = "$this/life_click.png"
            val lifeUnclick = "$this/life_unclick.png"
            val Locker = "$this/Dark_World_Lock.png"
            val crosshair = "$this/crosshair.png"
            val pause = "$this/pause.png"
            val play = "$this/play.png"
            val paused = "$this/paused.png"
            val ult = "$this/book_001.png"

            object Shuriken {
                override fun toString(): String = "$Objs/shuriken"
                val book = "$this/book.png"
                val normalGun = "$this/normalGun.png"
                val shotGun = "$this/shotGun.png"
                val machineGun = "$this/machineGun.png"
                val sniper = "$this/sniper.png"
                val rocket = "$this/rocket.png"
            }
        }

        object BackGround {
            override fun toString(): String = "$Imgs/backgrounds"

            val ground = "$this/TileFull.png"
            val groundUp = "$this/LeftSteps.png"
            val groundDown = "$this/RightSteps.png"
            val bg = "$this/mountain-bg.png"
            val foreground = "$this/mountain-foreground-trees.png"
            val mountain = "$this/mountain-mountain-far.png"
            val mountains = "$this/mountain-mountains.png"
            val trees = "$this/mountain-foreground-trees.png"
            val fog = "$this/fog.png"
            //            val trees = "$this/mountain-trees.png"
            val floor = "$this/floor.png"
            val test = "$this/test.png"
            val sky = "$this/background.png"
        }

        object StartScene {
            override fun toString(): String = "$Imgs/startScene"
            val gameName = "$this/unknown.png"
            val startSceneBackGround = "$this/startScene.png"
            val exit_button = "$this/exit_button.png"
            val readme_button = "$this/readme_button.png"
            val record_button = "$this/record_button.png"
            val gameStart_button = "$this/gameStart_button.png"
            val exit_button_click = "$this/exit_button_click.png"
            val readme_button_click = "$this/readme_button_click.png"
            val record_button_click = "$this/record_button_click.png"
            val gameStart_button_click = "$this/gameStart_button_click.png"
            val back_button = "$this/button_back.png"
            val back_button_click = "$this/button_back_click.png"
            val record_border = "$this/record_border.png"
            val readme_border = "$this/readme_border.png"
            val readme_paper = "$this/readmePaper.png"
            val selection = "$this/selection.png"
            val moon1 = "$this/moon2.png"
            val moon2 = "$this/moon1.png"
        }

        object EndScene {
            override fun toString(): String = "$Imgs/endScene"
            val backGround = "$this/background.png"
            val unAcademy = "$this/UnAcademy.png"
            val academy = "$this/Academy.png"
            val unGenin = "$this/UnGenin.png"
            val genin = "$this/Genin.png"
            val unChuuning = "$this/UnChuunin.png"
            val chuuning = "$this/Chuunin.png"
            val unJounin = "$this/UnJounin.png"
            val jounin = "$this/Jounin.png"
            val unHokage = "$this/UnHokage.png"
            val hokage = "$this/Hokage.png"
            val menu = "$this/button_menu.png"
            val menu_click = "$this/button_menu_click.png"
        }
    }

    object Sounds {
        override fun toString(): String = "sounds"

        val CLICK = "$this/click.wav"
        val RELOAD = "$this/reload.wav"

        object BackGround {
            override fun toString(): String = "$Sounds/BackGround"

            val BGM1 = "$this/backGroundMusic.wav"
            val BGM2 = "$this/backGroundMusic2.wav"
            val OVER1 = "$this/gameover.wav"
            val OVER2 = "$this/gameover2.wav"
        }

        object Bullets {
            override fun toString(): String = "$Sounds/Bullets"

            val CHIDORI1 = "$this/chidori.wav"
            val CHIDORI2 = "$this/chidori2.wav"
            val SHOT = "$this/shot.wav"
            val HIT = "$this/hit.wav"
            val ROCKET = "$this/RocketBoom2.wav"
            val ROCKETBOOM = "$this/RocketBoom.wav"
            val ROCK = "$this/RockRoll.wav"
            val ULT = "$this/ult.wav"
            val REFUND = "$this/sword_attack2.wav"

        }
    }

}