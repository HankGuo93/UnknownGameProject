package utils

object NinjaPath {
    override fun toString(): String = ""

    object Imgs {
        override fun toString(): String = "$NinjaPath/imgs"

        object Actors {
            override fun toString(): String = "$Imgs/actors"
            val ACTOR1 = "$this/Actor1.png"

            //separate path
            object ninja {
                object ninja1 {
                    override fun toString(): String = "$Actors/ninja1"
                }
                object ninja2 {
                    override fun toString(): String = "$Actors/ninja2"
                }
                object ninja3 {
                    override fun toString(): String = "$Actors/ninja3"
                }
                object ninja4 {
                    override fun toString(): String = "$Actors/ninja4"
                }
                object ninja5 {
                    override fun toString(): String = "$Actors/ninja5"
                }
                object ninja6 {
                    override fun toString(): String = "$Actors/ninja6"
                }
                object ninja7 {
                    override fun toString(): String = "$Actors/ninja7"
                }
                object machine1 {
                    override fun toString(): String = "$Actors/machine1"
                }
                object machine2 {
                    override fun toString(): String = "$Actors/machine2"
                }

                object machineAct {
                    object attack {
                        override  fun toString(): String = "/attack"
                        val attack1 = "$this/Attack1.png"
                        val attack2 = "$this/Attack2.png"
                        val attack3 = "$this/Attack3.png"
                        val attack4 = "$this/Attack4.png"
                        val attack5 = "$this/Attack5.png"
                        val attack6 = "$this/Attack6.png"
                        val attack7 = "$this/Attack7.png"
                        val attack8 = "$this/Attack8.png"
//                        val attack9 = "$this/Attack9.png"
                        val picArr = listOf(
                            attack1, attack2, attack3, attack4, attack5, attack6
                            , attack7, attack8
                        )
                    }
                    object idle {
                        override fun toString(): String = "/idle"
                        val idle1 = "$this/Idle1.png"
                        val idle2 = "$this/Idle2.png"
                        val idle3 = "$this/Idle3.png"
                        val idle4 = "$this/Idle4.png"
                        val picArr = listOf(
                            idle1, idle2, idle3, idle4
                        )
                    }
                    object walk {
                        override fun toString(): String = "/walk"
                        val walk1 = "$this/Walk1.png"
                        val walk2 = "$this/Walk2.png"
                        val walk3 = "$this/Walk3.png"
                        val walk4 = "$this/Walk4.png"
                        val walk5 = "$this/Walk5.png"
                        val walk6 = "$this/Walk6.png"
                        val picArr = listOf(
                            walk1, walk2, walk3, walk4, walk5, walk6
                        )
                    }
                }
                object ninjaAct {
                    object highJump {
                        override fun toString(): String = "/highJump"
                        val highJump000 = "$this/highJump_000.png"
                        val highJump001 = "$this/highJump_001.png"
                        val highJump002 = "$this/highJump_002.png"
                        val highJump003 = "$this/highJump_003.png"
                        val highJump004 = "$this/highJump_004.png"
                        val picArr = listOf<String>(
                            highJump000, highJump001, highJump002, highJump003, highJump004
                        )

                    }

                    object idle {
                        override fun toString(): String = "/idle"
                        val idle000 = "$this/idle_000.png"
                        val idle001 = "$this/idle_001.png"
                        val idle002 = "$this/idle_002.png"
                        val picArr = listOf<String>(
                            idle000, idle001, idle002
                        )

                    }

                    object jump {
                        override fun toString(): String = "/jump"
                        val jump000 = "$this/jump_000.png"
                        val picArr = listOf<String>(
                            jump000, jump000
                        )

                    }

                    object run {
                        override fun toString(): String = "/run"
                        val run000 = "$this/run_000.png"
                        val run001 = "$this/run_001.png"
                        val run002 = "$this/run_002.png"
                        val run003 = "$this/run_003.png"
                        val picArr = listOf<String>(
                            run000, run001, run002, run003
                        )

                    }

                    object throwAct {
                        override fun toString(): String = "/throw"
                        val throwAct000 = "$this/throw_000.png"
                        val throwAct001 = "$this/throw_001.png"
                        val throwAct002 = "$this/throw_002.png"
                        val throwAct003 = "$this/throw_003.png"
                        val throwAct004 = "$this/throw_004.png"
                        val throwAct005 = "$this/throw_005.png"
                        val throwAct006 = "$this/throw_006.png"
                        val picArr = listOf<String>(
                            throwAct000, throwAct001, throwAct002,
                            throwAct003, throwAct004, throwAct005,
                            throwAct006
                        )

                    }

                    object throwJump {
                        override fun toString(): String = "/throwJump"
                        val throwJump000 = "$this/throwJump_000.png"
                        val throwJump001 = "$this/throwJump_001.png"
                        val throwJump002 = "$this/throwJump_002.png"
                        val throwJump003 = "$this/throwJump_003.png"
                        val throwJump004 = "$this/throwJump_004.png"
                        val throwJump005 = "$this/throwJump_005.png"
                        val throwJump006 = "$this/throwJump_006.png"
                        val picArr = listOf<String>(
                            throwJump000, throwJump001, throwJump002,
                            throwJump003, throwJump004, throwJump005,
                            throwJump006
                        )

                    }

                    object throwRun {
                        override fun toString(): String = "/throwRun"
                        val throwRun000 = "$this/throwRun_000.png"
                        val throwRun001 = "$this/throwRun_001.png"
                        val throwRun002 = "$this/throwRun_002.png"
                        val throwRun003 = "$this/throwRun_003.png"
                        val throwRun004 = "$this/throwRun_004.png"
                        val throwRun005 = "$this/throwRun_005.png"
                        val throwRun006 = "$this/throwRun_006.png"
                        val throwRun007 = "$this/throwRun_007.png"
                        val throwRun008 = "$this/throwRun_008.png"
                        val picArr = listOf<String>(
                            throwRun000, throwRun001, throwRun002,
                            throwRun003, throwRun004, throwRun005,
                            throwRun006, throwRun007, throwRun008
                        )
                    }

                }
            }

//
//            object ninja6 {
//                override fun toString(): String = "$Actors/ninja6"
//
//                object highJump {
//                    override fun toString(): String = "$ninja6/highJump"
//                    val highJump000 = "$this/highJump_000.png"
//                    val highJump001 = "$this/highJump_001.png"
//                    val highJump002 = "$this/highJump_002.png"
//                    val highJump003 = "$this/highJump_003.png"
//                    val highJump004 = "$this/highJump_004.png"
//                    val picArr = listOf<String>(
//                            highJump000, highJump001, highJump002, highJump003, highJump004
//                    )
//
//                }
//
//                object idle {
//                    override fun toString(): String = "$ninja6/idle"
//                    val idle000 = "$this/idle_000.png"
//                    val idle001 = "$this/idle_001.png"
//                    val idle002 = "$this/idle_002.png"
//                    val picArr = listOf<String>(
//                            idle000, idle001, idle002
//                    )
//
//                }
//
//                object jump {
//                    override fun toString(): String = "$ninja6/jump"
//                    val jump000 = "$this/jump_000.png"
//                    val picArr = listOf<String>(
//                            jump000
//                    )
//
//                }
//
//                object run {
//                    override fun toString(): String = "$ninja6/run"
//                    val run000 = "$this/run_000.png"
//                    val run001 = "$this/run_001.png"
//                    val run002 = "$this/run_002.png"
//                    val run003 = "$this/run_003.png"
//                    val picArr = listOf<String>(
//                            run000, run001, run002, run003
//                    )
//
//                }
//
//                object throwAct {
//                    override fun toString(): String = "$ninja6/throw"
//                    val throwAct000 = "$this/throw_000.png"
//                    val throwAct001 = "$this/throw_001.png"
//                    val throwAct002 = "$this/throw_002.png"
//                    val throwAct003 = "$this/throw_003.png"
//                    val throwAct004 = "$this/throw_004.png"
//                    val throwAct005 = "$this/throw_005.png"
//                    val throwAct006 = "$this/throw_006.png"
//                    val picArr = listOf<String>(
//                            throwAct000, throwAct001, throwAct002,
//                            throwAct003, throwAct004, throwAct005,
//                            throwAct006
//                    )
//
//                }
//
//                object throwJump {
//                    override fun toString(): String = "$ninja6/throwJump"
//                    val throwJump000 = "$this/throwJump_000.png"
//                    val throwJump001 = "$this/throwJump_001.png"
//                    val throwJump002 = "$this/throwJump_002.png"
//                    val throwJump003 = "$this/throwJump_003.png"
//                    val throwJump004 = "$this/throwJump_004.png"
//                    val throwJump005 = "$this/throwJump_005.png"
//                    val throwJump006 = "$this/throwJump_006.png"
//                    val picArr = listOf<String>(
//                            throwJump000, throwJump001, throwJump002,
//                            throwJump003, throwJump004, throwJump005,
//                            throwJump006
//                    )
//
//                }
//
//                object throwRun {
//                    override fun toString(): String = "$ninja6/throwRun"
//                    val throwRun000 = "$this/throwRun_000.png"
//                    val throwRun001 = "$this/throwRun_001.png"
//                    val throwRun002 = "$this/throwRun_002.png"
//                    val throwRun003 = "$this/throwRun_003.png"
//                    val throwRun004 = "$this/throwRun_004.png"
//                    val throwRun005 = "$this/throwRun_005.png"
//                    val throwRun006 = "$this/throwRun_006.png"
//                    val throwRun007 = "$this/throwRun_007.png"
//                    val throwRun008 = "$this/throwRun_008.png"
//                    val picArr = listOf<String>(
//                            throwRun000,throwRun001,throwRun002,
//                            throwRun003,throwRun004,throwRun005,
//                            throwRun006,throwRun007,throwRun008
//                    )
//
//                }
//            }


        }

        object Objs {
            override fun toString(): String = "$Imgs/objs"

            val BULLET_NORMAL = "$this/boom.png"
            val BULLET_BOOM = "$this/boom2.png"

            object shuriken {
                override fun toString(): String = "$Objs/shuriken"

                object roll {
                    override fun toString(): String = "$shuriken/roll"
                    val roll000 = "$this/roll_000.png"
                    val roll001 = "$this/roll_001.png"
                    val roll002 = "$this/roll_002.png"
                    val roll003 = "$this/roll_003.png"
                    val picArr = listOf<String>(roll000, roll001, roll002, roll003)
                }
            }

            object gun {
                override fun toString(): String = "$Objs/gun"

                val machineGun_Arr = listOf<String>("$this/machineGun.png",)
                val gun_Arr = listOf<String>("$this/normalGun.png")
                val shotGun_Arr = listOf<String>("$this/shotGun.png")
                val sniper_Arr = listOf<String>("$this/sniper.png")
                val rocket_Arr = listOf<String>("$this/rocket.png")
                val BulletTripod = listOf("$this/BulletTripod.png")
            }

            object effect {
                override fun toString(): String = "$Objs/effect"

                val REFUND = listOf<String>("$this/refund.png",)

                object boomEffect {
                    override fun toString(): String = "$effect/boomEffect"

                    val boomE001 = "$this/explosion-e1.png"
                    val boomE002 = "$this/explosion-e2.png"
                    val boomE003 = "$this/explosion-e3.png"
                    val boomE004 = "$this/explosion-e4.png"
                    val boomE005 = "$this/explosion-e5.png"
                    val boomE006 = "$this/explosion-e6.png"
                    val boomE007 = "$this/explosion-e7.png"
                    val boomE008 = "$this/explosion-e8.png"
                    val boomE009 = "$this/explosion-e9.png"
                    val boomE010 = "$this/explosion-e10.png"
                    val endArr= listOf<String>(
                        boomE003,boomE004,boomE005,boomE006
                    )
                    val picArr = listOf<String>(
                        boomE001,boomE002,boomE003,boomE004,boomE005,boomE006,boomE007,boomE008,boomE009,boomE010
                    )

                }

                object boomSmoke {
                    override fun toString(): String = "$effect/boomSmoke"

                    val boomS001 = "$this/explosion-d1.png"
                    val boomS002 = "$this/explosion-d2.png"
                    val boomS003 = "$this/explosion-d3.png"
                    val boomS004 = "$this/explosion-d4.png"
                    val boomS005 = "$this/explosion-d5.png"
                    val boomS006 = "$this/explosion-d6.png"
                    val boomS007 = "$this/explosion-d7.png"
                    val boomS008 = "$this/explosion-d8.png"
                    val boomS009 = "$this/explosion-d9.png"

                    val picArr = listOf<String>(
                        boomS001,boomS002,boomS003,boomS004,boomS005,boomS006,boomS007,boomS008,boomS009
                    )

                }

                object bulletSmoke {
                    override fun toString(): String = "$effect/bulletSmoke"

                    val bulletS001 = "$this/explosion-c1.png"
                    val bulletS002 = "$this/explosion-c2.png"
                    val bulletS003 = "$this/explosion-c3.png"
                    val bulletS004 = "$this/explosion-c4.png"
                    val bulletS005 = "$this/explosion-c5.png"
                    val bulletS006 = "$this/explosion-c6.png"
                    val bulletS007 = "$this/explosion-c7.png"
                    val bulletS008 = "$this/explosion-c8.png"
                    val bulletS009 = "$this/explosion-c9.png"
                    val picArr = listOf<String>(
                        bulletS001,bulletS002,bulletS003,bulletS004,bulletS005,bulletS006,bulletS007,bulletS008,bulletS009
                       )

                }

                object deadSmoke {
                    override fun toString(): String = "$effect/deadSmoke"

                    val deadS001 = "$this/explosion-g1.png"
                    val deadS002 = "$this/explosion-g2.png"
                    val deadS003 = "$this/explosion-g3.png"
                    val deadS004 = "$this/explosion-g4.png"
                    val deadS005 = "$this/explosion-g5.png"
                    val deadS006 = "$this/explosion-g6.png"
                    val deadS007 = "$this/explosion-g7.png"
                    val deadS008 = "$this/explosion-g8.png"
                    val deadS009 = "$this/explosion-g9.png"
                    val picArr = listOf<String>(
                        deadS001,deadS002,deadS003,deadS004,deadS005,deadS006,deadS007,deadS008,deadS009
                    )

                }



            }

            object skill {
                override fun toString(): String = "$Objs/skill"

                val skillStart_Arr = listOf<String>(
                    "$this/hank1.png","$this/hank2.png","$this/hank3.png","$this/hank4.png"
                )

                val skillRoll_Arr = listOf<String>(
                    "$this/hank4.png","$this/hank5.png","$this/hank6.png",
                    "$this/hank7.png","$this/hank8.png"
                )

                val skillEnd_Arr = listOf<String>(
                    "$this/hank9.png", "$this/hank10.png","$this/hank11.png","$this/hank12.png",
                )

                val skill_Arr = listOf<String>(
                    "$this/hank1.png","$this/hank2.png","$this/hank3.png",
                    "$this/hank4.png","$this/hank5.png","$this/hank6.png",
                    "$this/hank7.png","$this/hank8.png","$this/hank9.png",
                    "$this/hank10.png","$this/hank11.png","$this/hank12.png",
                )

            }

        }
    }
}