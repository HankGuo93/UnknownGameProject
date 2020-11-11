package utils

object MarioPath {
    override fun toString(): String = ""

    object Imgs {
        override fun toString(): String = "$MarioPath/imgs"

        object Actors {
            override fun toString(): String = "$Imgs/actors"

            object hamrio {
                override fun toString(): String = "$Actors/hamrio"

                object hammer {
                    override fun toString(): String = "$hamrio/hammer"

                    val pic_Arr=listOf<String>(
                        "$this/hammer000.png","$this/hammer001.png","$this/hammer002.png","$this/hammer003.png"
                    )
                }

                object idle {
                    override fun toString(): String = "$hamrio/run"

                    val pic_Arr=listOf<String>(
                        "$this/run000.png"
                    )
                }

                object run {
                    override fun toString(): String = "$hamrio/run"

                    val pic_Arr=listOf<String>(
                        "$this/run000.png","$this/run001.png","$this/run000.png","$this/run002.png",
                    )
                }

                object shield {
                    override fun toString(): String = "$hamrio/shield"

                    val pic_Arr=listOf<String>(
                        "$this/shield000.png"
                    )
                }

                object throwHam {
                    override fun toString(): String = "$hamrio/throw"

                    val pic_Arr=listOf<String>(
                        "$this/throw000.png","$this/throw001.png"
                    )
                }

            }


        }
    }
}