package scene

import Internet.ClientCommands.Command
import core.GameKernel
import core.Scene
import core.controllers.ResController
import core.controllers.SceneController
import internet.client.ClientClass
import internet.client.CommandReceiver
import internet.server.Server
import mathUtils.Coordinate
import mathUtils.Vector
import obj.Actor
import obj.MainRole
//import obj.Puppet
import obj.bomb.Bomb
import obj.bullet.*
import obj.utils.GameInfo
import obj.utils.NinjaAnimator
import utils.Global
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.io.IOException
import java.util.*
import kotlin.properties.Delegates


class TestScene(gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {

//    private val ac = MainRole(50, 600)
    private val ac = Actor(50, 600)
    private var myID = 0

//    private var players: MutableList<Puppet> = mutableListOf()
    private var playersPrepare: MutableList<Int> = mutableListOf()
    private var ReadyToPlay: MutableList<Int> = mutableListOf()
    private var ammo: MutableList<BulletComm> = mutableListOf()
    private var playersAmmo: MutableList<MutableList<BulletComm>> = mutableListOf()

    private var bomb: Bomb? = null
    private var shotFlag = false

    private lateinit var hostData: Array<String>


    override fun sceneBegin() {
        Global.log("Game Start")
        myID = genServer() //本機創建server時，立即連線取得ID

        //測試用人偶
//        var p = Puppet(ac.collider.centerX + 100, ac.collider.centerY, myID)
//        players.add(p)
    }

    override fun sceneEnd() {
        ResController.instance.clear()
        Global.log("Game End")
    }

    fun genServer(): Int { //
        //產生Server
        val ss = Server(12345) //創建實體,於建構時給埠號(PORT)，再以start()方法啟動
        ss.start()
        hostData = ss.localAddress

        //創建第一次連線並取得ID
        return ClientClass.getInstance().id
    }

    private fun multiGameUpdate() {
        ClientClass.getInstance().consume { serialNum, commandCode, strs ->
            //檢查ID是否已存在
//            players.

            //不存在 則加入清單

            //若ID與自己的ID相同則return
            //                    if (serialNum == myId) {
            //                        return;
            //                    }
            var num = serialNum - 100 //從100開始編號
            //或用比對方式取得對應ID的位置 取出該人偶
//            var editPuppet = players[num]
            //指令處理
            when (commandCode) {
                Command.SELCHARACTER -> {
                    var tmpNinja: NinjaAnimator.Ninja? = null
                    when (strs?.get(0)) {
                        "1" -> tmpNinja = NinjaAnimator.Ninja.NINJA1
                        "2" -> tmpNinja = NinjaAnimator.Ninja.NINJA2
                        "3" -> tmpNinja = NinjaAnimator.Ninja.NINJA3
                        "4" -> tmpNinja = NinjaAnimator.Ninja.NINJA4
                        "5" -> tmpNinja = NinjaAnimator.Ninja.NINJA5
                        "6" -> tmpNinja = NinjaAnimator.Ninja.NINJA6
                        "7" -> tmpNinja = NinjaAnimator.Ninja.NINJA7
                    }
                    if (tmpNinja != null) {
//                        editPuppet.setCharacter(tmpNinja)
                    }
                }
                Command.PREPARESTATE -> {
                    when (strs?.get(0)) {
                        "F" -> playersPrepare[num] = 0  //取消OK時候，發送尚未準備完成
                        "T" -> playersPrepare[num] = 1  //按下OK時候，發送準備完成
                        "G" -> {
                            //if (!playersPrepare.contains(0)) {//
                            //    SceneController.instance.change(pubgScene()) //大家都為True時候，房主發出G 切換到遊戲場景
                            //}
                        }
                    }

                }
                Command.GAMEREADY -> {//準備好地圖或人物 物件
                    when (strs?.get(0)) {
                        "F" -> ReadyToPlay[num] = 0  //切換場景後 先送出物件 未建立完成
                        "T" -> ReadyToPlay[num] = 1  //物件建立完成後，發送準備完成 可自行判定收到全部完成 開始進行遊戲
                    }
                }
                Command.ACTORMOVE -> {//角色的位置
                    var x = strs?.get(0)?.toInt()
                    var y = strs?.get(1)?.toInt()
                    if (x != null && y != null) {
//                        editPuppet.setLocation(x, y)
                    }
                }
                Command.ACTORSTATE -> {//角色的動畫
                    var tmpState: NinjaAnimator.State = NinjaAnimator.State.IDLE
                    when (strs?.get(0)) {
                        "I" -> tmpState = NinjaAnimator.State.IDLE
                        "R" -> tmpState = NinjaAnimator.State.RUN
                        "T" -> tmpState = NinjaAnimator.State.THROWRUN
                        "D" -> tmpState = NinjaAnimator.State.DEADSMOKE
                        "J" -> tmpState = NinjaAnimator.State.HIGHJUMP
                    }
//                    editPuppet.setAnimatorState(tmpState)
                }
                Command.ACTORDIR -> {//角色的方向
                    var tmpDir = Global.Direction.RIGHT
                    when (strs?.get(0)) {
                        "L" -> tmpDir = Global.Direction.LEFT
                        "R" -> tmpDir = Global.Direction.RIGHT
                    }
//                    editPuppet.setPuppetDir(tmpDir)

                }
                Command.BULLETGEN -> {//每一發產生的子彈
                    var tmpBullet: BulletComm? = null
                    var tmpX = strs?.get(1)?.toInt()
                    var tmpY = strs?.get(2)?.toInt()
                    var tmpAngle = strs?.get(3)?.toDouble()
                    var tmpRange = strs?.get(4)?.toDouble()
                    if (tmpX != null && tmpY != null && tmpAngle != null && tmpRange!= null) {
                        when (strs?.get(0)) {
                            "1" -> tmpBullet = SingleB(tmpX, tmpY, tmpAngle, tmpRange)
                            "2" -> tmpBullet = BurstB(tmpX, tmpY, tmpAngle,tmpRange)
                            "3" -> tmpBullet = ShotB(tmpX, tmpY, tmpAngle,tmpRange)
                            "4" -> tmpBullet = SniperB(tmpX, tmpY, tmpAngle,tmpRange)
                            "5" -> tmpBullet = Shuriken(tmpX, tmpY, tmpAngle,tmpRange)
                        }
                    }
                    if (tmpBullet != null) {
                        //ammo.add(tmpBullet)
                        playersAmmo[num].add(tmpBullet)
                    }
                }
            }
        }

    }

    override fun update(timePassed: Long) {


        multiGameUpdate()

        ac.update(timePassed)
        ammo.forEach { a -> a.update(timePassed) }

        ac.operateWeapon.update(timePassed)
        bomb?.update(timePassed)

    }

    override fun paint(g: Graphics) {
        bomb?.shakeValue().let { c ->
            if (c != null) {
                g.translate(c.x.toInt(),c.y.toInt())
            }
        }
        ac.paint(g)
        ammo.forEach { a -> a.paint(g) }
        bomb?.paint(g)
        g.color = Color.yellow
        g.drawString(hostData[0].toString(), 100, 100)
        g.drawString(hostData[1].toString(), 300, 100)
        g.color = Color.black

    }

    override
    val input: ((GameKernel.Input.Event) -> Unit)?
        get() = { e ->
            run {
                ac.input?.invoke(e)
                when (e) {
                    is GameKernel.Input.Event.MouseDragged -> {

                        if (shotFlag) {
                            val tmp = Vector.xyToVector(
                                Coordinate(
                                    (e.data.x - ac.painter.centerX).toDouble(),
                                    (e.data.y - ac.painter.centerY).toDouble()
                                )
                            )
                            var b = ac.shot(
                                ac.painter.centerX,
                                ac.painter.centerY,
                                tmp.degree
                            )
                            if (!b.isNullOrEmpty()) {
                                b.forEach { a -> ammo.add(a) }
                            }

                        }
                    }

                    is GameKernel.Input.Event.MousePressed -> {
                        if (e.data.button == MouseEvent.BUTTON1) {
                            shotFlag = true
                            val tmp = Vector.xyToVector(
                                Coordinate(
                                    (e.data.x - ac.painter.centerX).toDouble(),
                                    (e.data.y - ac.painter.centerY).toDouble()
                                )
                            )
                            var b = ac.shot(
                                ac.painter.centerX,
                                ac.painter.centerY,
                                tmp.degree
                            )
                            if (!b.isNullOrEmpty()) {
                                b.forEach { a -> ammo.add(a) }
                            }
                        }
                        if (e.data.button == MouseEvent.BUTTON3) {
                            ac.reload()
                        }


                    }

                    is GameKernel.Input.Event.MouseReleased -> {
                        shotFlag = false
                        ac.operateWeapon.releaseWeapon()
                    }

                    is GameKernel.Input.Event.KeyPressed -> {
                        if (e.data.keyCode == KeyEvent.VK_SPACE) {
                            if (ac.dir == Global.Direction.RIGHT) {

                                bomb = Bomb(
                                    ac.collider.centerX,
                                    ac.collider.centerY,
                                    Global.SCREEN_X * 3 / 4,
                                    Global.SCREEN_Y * 1 / 3,
                                    ac.dir
                                )
                            } else if (ac.dir == Global.Direction.LEFT) {
                                bomb = Bomb(
                                    ac.collider.centerX,
                                    ac.collider.centerY,
                                    Global.SCREEN_X * 1 / 4,
                                    Global.SCREEN_Y * 1 / 3,
                                    ac.dir
                                )
                            }

                        }
                    }

                    else -> {
                    }
                }
            }
        }
}