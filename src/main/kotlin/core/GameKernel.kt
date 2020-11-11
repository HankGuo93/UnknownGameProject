package core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.awt.Canvas
import java.awt.Graphics
import java.awt.event.*

class GameKernel(private val updateFreq: Int, private val paintFreq: Int, private val gi: GameInterface) : Canvas() {

    private val nanoUnit = 1000000000L

    private val input = Input()

    private var gameLoop: Job? = null

    interface GameInterface {
        fun update(timePassed: Long)
        fun paint(g: Graphics)
        val input: ((Input.Event) -> Unit)?
    }

    init {
        addKeyListener(input)
        addMouseListener(input)
        addMouseMotionListener(input)
        addMouseWheelListener(input)
    }


    private fun paint() {
        val bs = bufferStrategy
        bs ?: run {
            createBufferStrategy(3)
            return
        }
        val g = bs.drawGraphics
        g.fillRect(0, 0, width, height)
        gi.paint(g)
        g.dispose()
        bs.show()
    }

    fun play(debug: Boolean) {
        if (gameLoop != null) {
            return
        }
        gameLoop = GlobalScope.launch {
            val startTime = System.nanoTime()
            var passedUpdated = 0
            var lastRepaintTime = System.nanoTime()
            var paintTimes = 0
            var timer = System.nanoTime()

            while (isActive) {
                val currentTime = System.nanoTime()
                val totalTime = currentTime - startTime
                val targetTotalUpdated = totalTime / (nanoUnit / updateFreq)
                while (passedUpdated < targetTotalUpdated) {
                    input.consumeInput(gi.input)
                    gi.update(totalTime)
                    passedUpdated++
                }
                if (currentTime - timer >= nanoUnit) {
                    if (debug) {
                        println("FPS: $paintTimes")
                    }
                    paintTimes = 0
                    timer = currentTime
                }
                if ((nanoUnit / paintFreq) <= currentTime - lastRepaintTime) {
                    lastRepaintTime = currentTime
                    paint()
                    paintTimes++
                }
            }
        }
    }

    class Input() : KeyListener, MouseListener, MouseMotionListener,
            MouseWheelListener {
        private val events = mutableListOf<Event>()

        override fun mouseReleased(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseReleased }) {
                    events += Event.MouseReleased(e)
                }
            }
        }

        override fun mouseEntered(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseEntered }) {
                    events += Event.MouseEntered(e)
                }
            }
        }

        override fun mouseClicked(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseReleased }) {
                    events += Event.MouseReleased(e)
                }
            }
        }

        override fun mouseExited(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseExited }) {
                    events += Event.MouseExited(e)
                }
            }
        }

        override fun mousePressed(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MousePressed }) {
                    events += Event.MousePressed(e)
                }
            }
        }

        override fun mouseMoved(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseMoved }) {
                    events += Event.MouseMoved(e)
                }
            }
        }

        override fun mouseDragged(e: MouseEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseDragged }) {
                    events += Event.MouseDragged(e)
                }
            }
        }

        override fun mouseWheelMoved(e: MouseWheelEvent) {
            synchronized(this) {
                if (!events.any { it is Event.MouseWheelMoved }) {
                    events += Event.MouseWheelMoved(e)
                }
            }
        }

        override fun keyTyped(event: KeyEvent) {
            synchronized(this) {
                if (!events.any { it is Event.KeyTyped && it.data.keyCode == event.keyCode }) {
                    events += Event.KeyTyped(event)
                }
            }
        }

        override fun keyPressed(event: KeyEvent) {
            synchronized(this) {
                if (!events.any {
                            (it is Event.KeyPressed && it.data.keyCode == event.keyCode) ||
                                    (it is Event.KeyKeepPressed && it.data.keyCode == event.keyCode)
                        }) {
                    events += Event.KeyPressed(event)
                    events += Event.KeyKeepPressed(event)
                }
            }
        }

        override fun keyReleased(event: KeyEvent) {
            synchronized(this) {
                if (!events.any { it is Event.KeyReleased && it.data.keyCode == event.keyCode }) {
                    events += Event.KeyReleased(event)
                    events.removeIf { it is Event.KeyKeepPressed && it.data.keyCode == event.keyCode }
                }
            }
        }

        fun consumeInput(consumer: ((Event) -> Unit)?) {
            synchronized(this) {
                consumer?.run {
                    events.forEach(consumer)
                }
                events.removeIf { it !is Event.KeyKeepPressed }
            }
        }

        sealed class Event {
            data class KeyTyped(val data: KeyEvent) : Event()
            data class KeyPressed(val data: KeyEvent) : Event()
            data class KeyKeepPressed(val data: KeyEvent) : Event()// solve delay-auto-shifted
            data class KeyReleased(val data: KeyEvent) : Event()
            data class MousePressed(val data: MouseEvent) : Event()
            data class MouseReleased(val data: MouseEvent) : Event()
            data class MouseEntered(val data: MouseEvent) : Event()
            data class MouseExited(val data: MouseEvent) : Event()
            data class MouseMoved(val data: MouseEvent) : Event()
            data class MouseDragged(val data: MouseEvent) : Event()
            data class MouseWheelMoved(val data: MouseWheelEvent) : Event()
        }
    }
}