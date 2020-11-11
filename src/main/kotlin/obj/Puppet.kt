package obj


import obj.utils.NinjaAnimator
import utils.Global
import java.awt.Graphics

class Puppet(x: Int, y: Int,val ID:Int) : MainRole(x, y) {


    init {

    }

    fun setCharacter(ninja: NinjaAnimator.Ninja) {
        this.ninja = ninja
        this.animator.setNinja(ninja)
    }

    fun setAnimatorState(state: NinjaAnimator.State) {
        this.state = state
    }

    fun setLocation(x: Int, y: Int) {
        var dx = collider.centerX - painter.centerX
        var dy = collider.centerY - painter.centerY

        collider.setCenter(x, y)
        painter.setCenter( x - dx, y - dy )
    }

    fun setPuppetDir( d: Global.Direction){
        this.dir = d
    }

    override fun paintComponent(g: Graphics) {
        if (lifecycle == LIFESTAGE.REMOVE) {
            return
        }

        animator.paint(
            dir,
            painter.left,
            painter.top,
            painter.width,
            painter.height, g
        )
    }

    override fun update(timePassed: Long) {
        when (lifecycle) {
            LIFESTAGE.ALIVE -> {
                if (hp <= 0) {
                    animator.setState(NinjaAnimator.State.DEADSMOKE)
                    lifeDelay.stop()
                    lifeDelay.countLimit = LIFESTAGE.DEADSMOKE.keepTime
                    lifeDelay.play()
                    lifecycle = LIFESTAGE.DEADSMOKE
                }
            }
            LIFESTAGE.DEADSMOKE -> {
                if (lifeDelay.count()) {
                    lifecycle = LIFESTAGE.REMOVE
                }
            }
            LIFESTAGE.REMOVE -> {
                return
            }
        }
        animator.update(timePassed)

    }

}
