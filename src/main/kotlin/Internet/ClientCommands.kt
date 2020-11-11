package Internet

import internet.client.ClientClass
import internet.client.CommandReceiver
import obj.MainRole
import obj.bullet.BulletComm
import obj.bullet.*
import obj.utils.NinjaAnimator
import utils.Global
import java.util.*
import kotlin.math.roundToInt


class ClientCommands {

    companion object Command {
        //靜態物件
        const val CONNECT = 0     //連線指令
        const val SELCHARACTER = 1     //ID,1~7
        const val PREPARESTATE = 2     //ID,1
        const val GAMEREADY = 3     //ID,1
        const val ACTORMOVE = 4     //ID,x,y,
        const val ACTORSTATE = 5     //ID,State
        const val ACTORDIR = 6     //角色ID,dir(0/1 L/R)
        const val BULLETGEN = 7     //子彈生成


        const val MAPITEM = 8        //地圖物件傳輸
        const val MAPOK = 9           //地圖完成傳輸


        //send commands
        fun connect(ip: String, port: Int): Int { //玩家join連線用 並回傳ID
            ClientClass.getInstance().connect(ip, port) //進行連線用
            return ClientClass.getInstance().id
        }

        fun sendSelectedCharacter(ninja: NinjaAnimator.Ninja) {
            var strs = ArrayList<String>()
            when (ninja) {  //發送選擇的角色
                NinjaAnimator.Ninja.NINJA1 -> strs.add("1")
                NinjaAnimator.Ninja.NINJA2 -> strs.add("2")
                NinjaAnimator.Ninja.NINJA3 -> strs.add("3")
                NinjaAnimator.Ninja.NINJA4 -> strs.add("4")
                NinjaAnimator.Ninja.NINJA5 -> strs.add("5")
                NinjaAnimator.Ninja.NINJA6 -> strs.add("6")
                NinjaAnimator.Ninja.NINJA7 -> strs.add("7")
            }
            ClientClass.getInstance().sent(Command.SELCHARACTER, strs)
        }

        fun sendPrepare(p: Int = 0) {
            var strs = ArrayList<String>()
            when(p){
                0 -> strs.add("F")  //取消OK時候，發送尚未準備完成
                1 -> strs.add("T")  //按下OK時候，發送準備完成
                2 -> strs.add("G") //大家都為T時候，房主發出G 切換到遊戲場景
            }
            ClientClass.getInstance().sent(Command.PREPARESTATE, strs)
        }

        fun gameReady(p: Boolean = false) {
            var strs = ArrayList<String>()
            when(p){
                false   -> strs.add("F")  //初始狀態，地圖尚未準備完成
                true    -> strs.add("T")  //產生地圖 及 人物 完成，發送準備完成
            }

            ClientClass.getInstance().sent(Command.GAMEREADY, strs)
        }

        fun sendLocation(ac: MainRole) {
            var strs = ArrayList<String>()
            strs.add(ac.collider.centerX.toString())
            strs.add(ac.collider.centerY.toString())
            ClientClass.getInstance().sent(Command.ACTORMOVE, strs)
        }

        fun sendState(state: NinjaAnimator.State) {
            var strs = ArrayList<String>()
            when (state) {
                NinjaAnimator.State.IDLE -> strs.add("I")
                NinjaAnimator.State.RUN -> strs.add("R")
                NinjaAnimator.State.THROWRUN -> strs.add("T")
                NinjaAnimator.State.DEADSMOKE -> strs.add("D")
                NinjaAnimator.State.HIGHJUMP -> strs.add("J")
            }
            ClientClass.getInstance().sent(Command.ACTORSTATE, strs)
        }

        fun sendDir(ac: MainRole) {
            var strs = ArrayList<String>()
            when (ac.dir) {
                Global.Direction.LEFT   ->  strs.add("L")
                Global.Direction.RIGHT  ->  strs.add("R")
            }
            ClientClass.getInstance().sent(Command.ACTORDIR, strs)
        }

        fun sendNewBullet(b: BulletComm) {
            var strs = ArrayList<String>()
            when (b) {
                is SingleB  -> strs.add("1")
                is BurstB   -> strs.add("2")
                is ShotB    -> strs.add("3")
                is SniperB  -> strs.add("4")
                is Shuriken -> strs.add("5")
            }
            strs.add(b.collider.centerX.toString())
            strs.add(b.collider.centerY.toString())
            strs.add(b.flyAngle.roundToInt().toString())
            strs.add(b.maxRange.roundToInt().toString())

            ClientClass.getInstance().sent(Command.BULLETGEN, strs)
        }

    }

    //下方為接收時候的參考code
    //需要實現的地方保留需要的部分
/*
    private fun multiGameUpdate(){
        ClientClass.getInstance().consume { serialNum, commandCode, strs ->
            //檢查ID是否已存在
            //不存在 則加入清單

            //若ID與自己的ID相同則return
            //if (serialNum == myId) {
            //    return;
            //}
            var num = serialNum - 100 //從100開始編號
            //或用比對方式取得對應ID的位置 取出該人偶
            var editPuppet = players[num]
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
                        editPuppet.setCharacter(tmpNinja)
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
                        editPuppet.setLocation(x, y)
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
                    editPuppet.setAnimatorState(tmpState)
                }
                Command.ACTORDIR -> {//角色的方向
                    var tmpDir = Global.Direction.RIGHT
                    when (strs?.get(0)) {
                        "L" -> tmpDir = Global.Direction.LEFT
                        "R" -> tmpDir = Global.Direction.RIGHT
                    }
                    editPuppet.setPuppetDir(tmpDir)

                }
                Command.BULLETGEN -> {//每一發產生的子彈
                    var tmpBullet: BulletComm? = null
                    var tmpX = strs?.get(1)?.toInt()
                    var tmpY = strs?.get(2)?.toInt()
                    var tmpAngle = strs?.get(3)?.toDouble()
                    if (tmpX != null && tmpY != null && tmpAngle != null) {
                        when (strs?.get(0)) {
                            "1" -> tmpBullet = SingleB(tmpX, tmpY, tmpAngle)
                            "2" -> tmpBullet = BurstB(tmpX, tmpY, tmpAngle)
                            "3" -> tmpBullet = ShotB(tmpX, tmpY, tmpAngle)
                            "4" -> tmpBullet = SniperB(tmpX, tmpY, tmpAngle)
                            "5" -> tmpBullet = Shuriken(tmpX, tmpY, tmpAngle)
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
*/



}

