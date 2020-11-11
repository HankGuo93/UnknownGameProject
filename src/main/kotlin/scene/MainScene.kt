package scene

import audio.AudioResourceController
import core.GameKernel
import core.Scene
import core.controllers.ResController
import core.controllers.SceneController
import core.utils.Delay
import internet.client.ClientClass
import mathUtils.Coordinate
import mathUtils.Vector
import obj.Armory.BurstShot
import obj.Armory.Postion
import obj.Armory.Scroll
import obj.Armory.ShotGun
import obj.Enemy
import obj.Machine
import obj.MainRole
import obj.background.*
import obj.bomb.Bomb
import obj.bullet.*
import obj.button.*
import obj.utils.GameInfo
import obj.utils.GameObject
import obj.utils.NinjaAnimator
import obj.utils.Timer
import utils.Global
import utils.Path
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.io.File
import java.io.InputStream
import kotlin.math.roundToInt
import kotlin.random.Random

class MainScene(gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {
    private var file: InputStream? = javaClass.getResourceAsStream("/font/pixelFJ8pt1__.TTF")
    private var font: Font = Font.createFont(Font.TRUETYPE_FONT, file)
    private var pause: PauseButton = PauseButton(
        Global.SCREEN_X * 95 / 100, Global.SCREEN_Y * 5 / 100,
        Path.Imgs.Objs.pause, Path.Imgs.Objs.play, 64, 64
    )
    private var crossHair: Image = ResController.instance.image(Path.Imgs.Objs.crosshair)
    private var timer: Timer = Timer()
    private val headshot: Image = ResController.instance.image(Path.Imgs.Objs.headshot)
    private val moon1: Image = ResController.instance.image(Path.Imgs.StartScene.moon1)
    private val moon2: Image = ResController.instance.image(Path.Imgs.StartScene.moon2)
    private var delayPaint: Delay = Delay(60)
    private var delayHint: Delay = Delay(300)
    private val display: Int = 600
    private var delayDisplay: Delay = Delay(display)
    private var postion: Postion = Postion(-100, -100)
    private var mapProductArr: MutableList<MutableList<StairObject>> = mutableListOf()
    private var mountainFar = MountainFar(Global.SCREEN_X / 2, Global.SCREEN_Y / 2) //設定在畫面中間
    private var trees = Trees(Global.SCREEN_X / 2, Global.SCREEN_Y / 2)
    private var ammo: MutableList<BulletComm> = mutableListOf()
    private var enemyAmmo: MutableList<BulletComm> = mutableListOf()
    private var mountains = Mountains(Global.SCREEN_X / 2, Global.SCREEN_Y / 2)
    private val floorButtonA = Floor((Global.SCREEN_X / 2), (Global.SCREEN_Y * 8 / 10))
    private val floorButtonB = Floor(floorButtonA.collider.right * 15 / 10, (Global.SCREEN_Y * 8 / 10))
    private val mainRole = MainRole(100, 200)
    private var bomb: Bomb? = null
    private var shakeX: Int = 0
    private var shakeY: Int = 0
    private var fog: Fog = Fog(0, floorButtonA.painter.top-100)
    private var mousePoint: Point = Point(0, 0)
    private var henceTime: Boolean = false
    private var henceTimeDoubleCheck: Boolean = false
    private val enemyArr: MutableList<Enemy> = mutableListOf()
    private val machineArr: MutableList<Machine> = mutableListOf()
    private val henceState: MutableList<SceneButton> = mutableListOf()
    private var hint: Boolean = true
    private var singleShotBorder: SingleShotBorder =
        SingleShotBorder(Global.SCREEN_X / 3, Global.SCREEN_Y * 9 / 10, mainRole.getWeapon(0)).apply {
            this.lock = false
        }
    private var burstShotBorder: BurstShotBorder = BurstShotBorder(
        singleShotBorder.collider.right + Global.SCREEN_X / 20,
        Global.SCREEN_Y * 9 / 10,
        mainRole.getWeapon(1)
    )
    private var shotGunBorder: ShotGunBorder = ShotGunBorder(
        burstShotBorder.collider.right + Global.SCREEN_X / 20,
        Global.SCREEN_Y * 9 / 10,
        mainRole.getWeapon(2)
    )
    private var sniperBorder: SniperBorder = SniperBorder(
        shotGunBorder.collider.right + Global.SCREEN_X / 20,
        Global.SCREEN_Y * 9 / 10,
        mainRole.getWeapon(3)
    )
    private var mrlBorder: MRLBorder = MRLBorder(
        sniperBorder.collider.right + Global.SCREEN_X / 20,
        Global.SCREEN_Y * 9 / 10,
        mainRole.getWeapon(4)
    )
    private var chakuraBorder: ChakuraBorder = ChakuraBorder(
        mrlBorder.collider.right + Global.SCREEN_X / 20,
        Global.SCREEN_Y * 9 / 10,
        mainRole.getWeapon(5)
    )
    private var backGround: BackGround = BackGround(Global.SCREEN_X / 2, Global.SCREEN_Y / 2)
    private var client: ClientClass = ClientClass.getInstance()
    private var shotFlag = false
    private var clickAble = true
    private var headShot: Boolean = false
    private var scroll: Scroll? = null

    private var shake: Boolean = true

    private var goldenFingerStringArray: MutableList<String> = mutableListOf()
    private var gFStr = ""
    private var hankFlag = false
    private var kimFlag = false

    init {
        goldenFingerStringArray.add("Hank")
        goldenFingerStringArray.add("KimCreate")
        goldenFingerStringArray.add("Mario")
    }

    override fun sceneBegin() {
        delayHint.play()
        AudioResourceController.getInstance().stop(Path.Sounds.BackGround.BGM1)//關閉選單音樂
        AudioResourceController.getInstance().loop(Path.Sounds.BackGround.BGM2, Integer.MAX_VALUE / 2)

        this.gameInfo = GameInfo()
        Global.log("Game Start")
        singleShotBorder.state = true
        mapProductArr = StairObjectCreator(amount = 15, distance = 4, heelHeight = 3, random = 8).mapObjectCreator()
        henceState.add(
            SceneButton(
                Global.SCREEN_X * 3 / 8,
                200,
                Path.Imgs.Objs.attackUnclick,
                Path.Imgs.Objs.attackClick,
                width = Global.SCREEN_X / 8
            )
        )
        henceState.add(
            SceneButton(
                Global.SCREEN_X * 4 / 8 + 30,
                200,
                Path.Imgs.Objs.defenceUnclick,
                Path.Imgs.Objs.defenceClick,
                width = Global.SCREEN_X / 8
            )
        )
        henceState.add(
            SceneButton(
                Global.SCREEN_X * 5 / 8 + 60,
                200,
                Path.Imgs.Objs.lifeUnclick,
                Path.Imgs.Objs.lifeClick,
                width = Global.SCREEN_X / 8
            )
        )
    }

    override fun sceneEnd() {

        AudioResourceController.getInstance().stop(Path.Sounds.BackGround.BGM2)
        ResController.instance.clear()
        Global.log("Game End")
    }

    fun bulletGenSound(b: BulletComm) {
        //子彈生成時 產生音效
        when (b) {
            is Rocket -> {
                AudioResourceController.getInstance()
                    .play(Path.Sounds.Bullets.ROCKET)
            }
            is RocketBoom -> {
                //生成時 不撥放音效 只做碰撞音效
            }
            is Chidori -> {
                AudioResourceController.getInstance()
                    .play(Path.Sounds.Bullets.CHIDORI2)
            }
            is MachineBullet -> {
                AudioResourceController.getInstance()
                    .play(Path.Sounds.Bullets.ROCKET)
            }
            is RefundEffect -> {
                AudioResourceController.getInstance()
                    .play(Path.Sounds.Bullets.REFUND)
            }
            else -> {
                AudioResourceController.getInstance()
                    .play(Path.Sounds.Bullets.SHOT)
            }
        }
    }

    fun bulletHitSound(b: BulletComm) {
        //播放子彈擊中音效
        if (b.lifecycle == BulletComm.LIFESTAGE.FLY) {
            when (b) {
                is Rocket -> {
                    //生成 RocketBoom造成傷害 並改用RocketBoom碰撞音效
                }
                is RocketBoom -> {
                    AudioResourceController.getInstance()
                        .play(Path.Sounds.Bullets.ROCKETBOOM)
                }
                is Chidori -> {
                    //千鳥狀態改變時 子彈內狀態變化 撥放一次
                }
                is MachineBullet -> {
                    AudioResourceController.getInstance()
                        .play(Path.Sounds.Bullets.ROCKETBOOM)
                }
                else -> {
                    AudioResourceController.getInstance()
                        .play(Path.Sounds.Bullets.HIT)
                }
            }
        }
    }

    override fun update(timePassed: Long) {
        pause.update(timePassed)
        if (!pause.click) {
            if (delayHint.count()) {
                this.hint = false
            }
            scroll?.update(timePassed)
            if (scroll != null) {
                if (scroll?.standBy!! && !scroll?.disappear!!) {
                    scroll?.shooting(mainRole.collider.centerX, mainRole.collider.centerY)?.let {
                        it.forEach { a ->
                            bulletGenSound(a)
                            enemyAmmo.add(a)
                        }
                    }
                }
                if (scroll?.remove!!) {
                    scroll = null
                }
            }
            val ammoTmp: MutableList<BulletComm> = mutableListOf()
            collection(mainRole)
            mainRole.update(timePassed)
            bomb?.update(timePassed)
            postion.update(timePassed)
            if (postion.isCollision(mainRole)) {
                mainRole.hp += 300
                postion.translate(y = 1000)
            }
            gameInfo.update()
            if ((gameInfo.distance / 100) % 100 == 0 && gameInfo.distance / 100 >= 100) {
                postion = Postion(mainRole.collider.centerX, -50)
            }
            if ((gameInfo.distance / 100) % 50 == 1 && henceTimeDoubleCheck) {
                henceTimeDoubleCheck = false
            }
            if ((gameInfo.distance / 100) % 50 == 0 && !henceTimeDoubleCheck) {
                delayDisplay.play()
                timer = Timer(System.nanoTime())
                henceTime = true
                henceTimeDoubleCheck = true
            }
            if (delayDisplay.count()) {
                henceTime = false
            }
            if ((gameInfo.distance / 100) % 75 == 0 && gameInfo.distance / 100 >= 1) {
                scroll = Scroll()
            }
            if (shotFlag && !mainRole.isShield) {
                val tmp = Vector.xyToVector(
                    Coordinate(
                        (mousePoint.x - mainRole.painter.centerX).toDouble(),
                        (mousePoint.y - mainRole.painter.centerY).toDouble()
                    )
                )
                var b = mainRole.shot(
                    mainRole.painter.centerX,
                    mainRole.painter.centerY,
                    tmp.degree
                )
                if (!b.isNullOrEmpty()) {
                    b.forEach { a ->
                        bulletGenSound(a)
                        ammo.add(a)
                        if (mainRole.ultLoading < 100) {
                            mainRole.ultLoading += 1
                        }
                    }
                }
            }
            if (!kimFlag && enemyArr.size < 2 * gameInfo.level && (Random.nextInt(60)==0) ) { //加入機率產生敵人，降低敵人生成速度
                when {
                    gameInfo.level < 2 -> {
                        enemyArr.add(Enemy(Random.nextInt(1600, 2100), 300, gameInfo.level * 2).apply {
                            this.hpMax = gameInfo.level * 100
                            this.attack = gameInfo.level * 5
                        })
                    }
                    gameInfo.level < 3 -> {
                        when (Random.nextInt(0, 5)) {
                            0 -> enemyArr.add(Enemy(Random.nextInt(0, 500), 300, gameInfo.level * 2).apply {
                                this.hpMax = gameInfo.level * 110
                                this.attack = gameInfo.level * 5
                            })
                            1 -> enemyArr.add(Enemy(Random.nextInt(1600, 2100), 300, gameInfo.level * 2).apply {
                                this.hpMax = gameInfo.level * 110
                                this.attack = gameInfo.level * 5
                            })
                            2 -> enemyArr.add(Enemy(Random.nextInt(1600, 2100), 300, gameInfo.level * 2).apply {
                                this.hpMax = gameInfo.level * 110
                                this.attack = gameInfo.level * 5
                                this.skyMode = true
                            })
                        }
                    }
                    else -> {
                        when (Random.nextInt(0, 8)) {
                            0 -> enemyArr.add(Enemy(Random.nextInt(0, 500), 300, gameInfo.level * 2).apply {
                                hpMax = gameInfo.level * 130
                                attack = gameInfo.level * 5
                            })
                            1 -> enemyArr.add(Enemy(Random.nextInt(1600, 2100), 300, gameInfo.level * 2).apply {
                                hpMax = gameInfo.level * 130
                                attack = gameInfo.level * 5
                            })
                            2 -> enemyArr.add(Enemy(Random.nextInt(1600, 2100), 300, gameInfo.level * 2).apply {
                                hpMax = gameInfo.level * 130
                                attack = gameInfo.level * 5
                            })
                            3 -> enemyArr.add(Enemy(Random.nextInt(0, 500), 300, gameInfo.level * 2).apply {
                                hpMax = gameInfo.level * 130
                                attack = gameInfo.level * 5
                                this.skyMode = true
                            })
                            4 -> enemyArr.add(Enemy(Random.nextInt(1600, 2100), 300, gameInfo.level * 2).apply {
                                hpMax = gameInfo.level * 130
                                attack = gameInfo.level * 5
                                this.skyMode = true
                            })
                            5 -> enemyArr.add(Machine(Random.nextInt(1600, 2100), 300).apply {
                                hpMax = gameInfo.level * 200
                                attack = gameInfo.level * 10
                            })
                        }
                    }
                }
            }
            ammo.forEach { a ->
                a.update(timePassed)
                enemyArr.forEach { e ->
                    if (a.isCollision(e) && e.lifecycle == MainRole.LIFESTAGE.ALIVE) {

                        if (e !is Machine && e.collider.bottom - a.collider.bottom >= e.collider.height * 2 / 5) { //敵人(人類) 的 碰撞框 底部 - 子彈底部 > 敵人身長的2/5
                            if (mainRole.operateWeapon == mainRole.getWeapon(3)) {
                                headShot = true
                                delayPaint.play()
                            }
                            if(kimFlag  && a.criticalDamage != 0){
                                println("e.hp = "+e.hp)
                            }
                            e.hp -= a.criticalDamage
                            if(kimFlag  && a.criticalDamage != 0){
                                println("criDamage = "+ a.criticalDamage)
                                println("e.hp = "+e.hp)
                            }
                        } else {
                            if(kimFlag && a.damage != 0){
                                println("e.hp = "+e.hp)
                            }
                            e.hp -= a.damage
                            if(kimFlag  && a.damage != 0){
                                println("damage = "+ a.damage)
                                println("e.hp = "+e.hp)
                            }
                        }

                        if (a.lifecycle == BulletComm.LIFESTAGE.FLY) {
                            bulletHitSound(a)
                        }
                        var tmp = a.boomSmoke()
                        if (tmp != null) {
                            ammoTmp.add(tmp)
                        }
                        if (e.hp <= 0) {
                            gameInfo.money += 100
                            gameInfo.killCount += 1
                        }
                    }
                }

                if (hitFloor(a, mapProductArr)) {
//                    a.boomSmoke()
                    var tmp = a.boomSmoke()
                    if (tmp != null && tmp is RocketBoom) {
                        ammoTmp.add(tmp)
                    }
                }
            }
            ammo.addAll(ammoTmp)
            var tmpReBullet: MutableList<BulletComm> = mutableListOf()
            enemyAmmo.forEach { a ->
                if (mainRole.shield != null) {
                    if (a.lifecycle == BulletComm.LIFESTAGE.FLY && a.isCollision(mainRole.shield!!)) {
                        if (mainRole.shield!!.refund) {
                            var refundAngle = 180 + Random.nextInt(-2, 2)
                            var rB=RefundEffect(
                                a.collider.centerX,
                                a.collider.centerY,
                                refundAngle+a.flyAngle,
                                500.0
                            )
                            bulletGenSound(rB)
                            ammo.add(rB) //加入盾反特效 無傷害
                            if (mainRole.ultLoading < 100) { //盾反發生時 集氣多一些
                                mainRole.ultLoading += 2
                            }
                            a.flyAngle += refundAngle
                            tmpReBullet.add(a)
                        } else {
                            if (a.lifecycle == BulletComm.LIFESTAGE.FLY) {
                                bulletHitSound(a)
                            }
                            a.boomSmoke()
                        }

                    }
                }
                if (bomb != null && a.isCollision(bomb as GameObject)) {
                    if (a.lifecycle == BulletComm.LIFESTAGE.FLY) {
                        bulletHitSound(a)
                    }
                    a.boomSmoke()
                }
                if (a.isCollision(mainRole)) {
                    if(hankFlag){
                        mainRole.hp -= a.damage/3
                    }else{
                        mainRole.hp -= a.damage
                    }

//                    if(Global.IS_DEBUG &&  a.damage !=0){
//                        println("hit mainRole:"+ a.damage)
//                    }
                    if (a.lifecycle == BulletComm.LIFESTAGE.FLY) {
                        bulletHitSound(a)
                    }
                    a.boomSmoke()
                }
                if (hitFloor(a, mapProductArr)) {
                    a.boomSmoke()
                }
                a.update(timePassed)
            }

            tmpReBullet.forEach { b ->
                b.damage += (mainRole.attack*1.1).roundToInt() //重設盾反武器傷害
                b.criticalDamage = b.damage
                ammo.add(b)
                enemyAmmo.remove(b)
            }


            enemyArr.forEach { a ->
                collection(a)

                if (a.collider.centerX < 0) {
                    var dy = -500
                    if (a.skyMode) {
                        dy = 0
                    }
                    a.translate(2000, dy)
                }
                a.mm(Point(mainRole.collider.centerX, mainRole.collider.centerY))

                //使用敵人數量 修正(降低)開槍機率 若兩個敵人 0~360 => 兩人 * 60/360 => 六秒開兩槍 -> 1bullet/3sec
                //若敵人 4人 0~600 => 4人 * 60/600 10秒開4槍  1bullet/2.5   最多趨近每 2秒 一發子彈

                if ((a.shotCount > 0) || (Random.nextInt(0, 60 + enemyArr.size * 60) == 0)) { //開槍次數尚未為0 機率開槍
//                if ( Random.nextInt(0, 60 + enemyArr.size * 60) == 0 ) { //開槍次數尚未為0 機率開槍
                    if (a.shotCount == 0) { //新增隨機開槍數
                        if (a.operateWeapon is BurstShot) {
                            a.shotCount = Random.nextInt(2,4) //機槍 連開3發
                        } else {
                            a.shotCount = 1
                        }
                    }
                    a.shot(mainRole.collider.centerX, mainRole.collider.centerY).let {
                        if (!(it.isNullOrEmpty())) {
                            a.shotCount -= 1
                            it.forEach { b ->
                                bulletGenSound(b)
                                enemyAmmo.add(b)
                            }
                        }
                    }
                } else if (Random.nextInt(0, 8) != 0) {
                    var distance: Int = a.collider.centerX - mainRole.collider.centerX
                    //增加敵人移動位置分布
                    if (distance + Random.nextInt(
                            -200,
                            200
                        ) > Global.WINDOW_WIDTH / 3 || distance + Random.nextInt(-200, 200) < -Global.WINDOW_WIDTH / 3
                    ) {
                        a.move(mainRole.collider.centerX)
                        a.move(mainRole.collider.centerX)
                    } else {
//                        a.mm(Point(mainRole.collider.centerX,mainRole.collider.centerY))
                        a.move(a.collider.centerX)
                    }
                }
                if (bomb != null && a.isCollision(bomb as GameObject)) {
                    a.hp -= 9999
                    if (a.hp <= 0) {
                        gameInfo.money += 100
                        gameInfo.killCount += 1
                    }
                }
                a.update(timePassed)
            }
            enemyArr.removeIf { a -> a.lifecycle == MainRole.LIFESTAGE.REMOVE }
            if (floorButtonA.collider.right < 0) {
                floorButtonA.translate(floorButtonB.collider.right * 2)
            }
            if (floorButtonB.collider.right < 0) {
                floorButtonB.translate((floorButtonA.collider.right) * 2)
            }


            if (mapProductArr.size < 15) { //隨機產生地圖物件
                mapProductArr.add(
                    StairObjectCreator(
                        amount = 15, distance = 3,
                        heelHeight = 3 + gameInfo.level,
                        random = 10 * gameInfo.level
                    ).addMapObject()
                )
            }

            for (mapArr in mapProductArr) {  //判斷降落地面停止
                for (i in 0 until mapArr.size) {
                    if (i - 1 >= 0 && mapArr[i].collider.bottom >= mapArr.get(i - 1).collider.top
                        && mapArr[i].collider.left == mapArr[i - 1].collider.left
                    ) {
                        mapArr[i].translate(y = -(mapArr[i].collider.bottom - mapArr.get(i - 1).collider.top))
                    } else if (mapArr[i].collider.bottom < floorButtonA.collider.top) {
                        mapArr[i].update(timePassed)
                    } else {
                        mapArr[i].translate(y = -(mapArr.get(i).collider.bottom - floorButtonA.collider.top))
                    }
                }
            }
            mapProductArr.removeIf { it[it.size - 1].collider.right <= 0; } //地圖物件離開畫面刪除
            ammo.removeIf { a -> a.outOfScreen }
            enemyAmmo.removeIf { a -> a.outOfScreen }
            if (bomb != null && bomb!!.outOfScreen) {
                bomb = null
            }
            var shurikenFix: Double = 0.0
            if (mainRole.collider.centerX > Global.SCREEN_CENTER_X) { //鏡頭控制
                postion.translate(-Global.ROLE_SPEED)
                mainRole.translate(-Global.ROLE_SPEED)
                for (mapArr in mapProductArr) {
                    for (map in mapArr) {
                        shurikenFix = if (mainRole.moveType == MainRole.MoveType.NULL) {
                            map.translate(-Global.ROLE_SPEED)
                            -Global.ROLE_SPEED.toDouble()
                        } else {
                            map.translate(-mainRole.moveType.x(Global.c))
                            -mainRole.moveType.x(Global.c)
                        }
                    }
                }
                fog.xPosition += (mainRole.moveType.x(Global.c)).toFloat()/10
                enemyArr.forEach { a ->
                    a.translate(-Global.ROLE_SPEED)
                }
                gameInfo.distance += Global.ROLE_SPEED
                if (kimFlag ) {
                    gameInfo.distance += Global.ROLE_SPEED * 2 //3倍速
                }
                mountainFar.paintShift(Global.ROLE_SPEED * 1.0 / 4)
                mountains.paintShift(Global.ROLE_SPEED * 2.0 / 4)
                trees.paintShift(Global.ROLE_SPEED * 3.0 / 4)
                floorButtonA.translate(-Global.ROLE_SPEED)
                floorButtonB.translate(-Global.ROLE_SPEED)
            }
            ammo.forEach { a -> a.camaraFix(shurikenFix) }
            enemyAmmo.forEach { a -> a.camaraFix(shurikenFix) }

            singleShotBorder.updateWeaponInfo(mainRole.getWeapon(0))
            burstShotBorder.updateWeaponInfo(mainRole.getWeapon(1))
            shotGunBorder.updateWeaponInfo(mainRole.getWeapon(2))
            sniperBorder.updateWeaponInfo(mainRole.getWeapon(3))
            mrlBorder.updateWeaponInfo(mainRole.getWeapon(4))
            chakuraBorder.updateWeaponInfo(mainRole.getWeapon(5))
            if (delayPaint.count()) {
                headShot = false
            }
            gameInfo.update()


            var distance = gameInfo.distance / 100
            burstShotBorder.apply {
                lock = true
                if (distance > unlockdistance) {
                    lock = false
                }
            }
            shotGunBorder.apply {
                lock = true
                if (distance > unlockdistance) {
                    lock = false
                }
            }
            sniperBorder.apply {
                lock = true
                if (distance > unlockdistance) {
                    lock = false
                }
            }
            mrlBorder.apply {
                lock = true
                if (distance > unlockdistance) {
                    lock = false
                }
            }
            chakuraBorder.apply {
                lock = true
                if (distance > unlockdistance) {
                    lock = false
                }
            }

            if (kimFlag || hankFlag) {
                burstShotBorder.lock = false
                shotGunBorder.lock = false
                sniperBorder.lock = false
                mrlBorder.lock = false
                chakuraBorder.lock = false
            }

            if (kimFlag ) {
                if (mainRole.hp <= mainRole.hpMax / 3) {
                    mainRole.hp = mainRole.hpMax
                }
                if (gameInfo.money < 5000) {
                    gameInfo.money += 5000
                }
            }

            if (mainRole.hp <= 0) {
                SceneController.instance.change(GameOverScene())
            }
            if (gameInfo.level >= 2) {
                fog.update(timePassed)
            }
        }
    }

    override fun paint(g: Graphics) {
        if (shakeX != 0 || shakeY != 0) {
            g.translate(shakeX, shakeY)
            shakeX = 0
            shakeY = 0
        }
        bomb?.shakeValue().let { c ->
            if (c != null) {
                shakeX += c.x.roundToInt()
                shakeY += c.y.roundToInt()
            }
        }

        backGround.paint(g)
        g.drawImage(moon2, Global.SCREEN_X * 1 / 2 - 235, -35, 440, 280, null)
        g.drawImage(moon1, Global.SCREEN_X * 1 / 4 + 20, -118, 780, 560, null)
        mountainFar.paint(g)
        mountains.paint(g)
        trees.paint(g)
        postion?.paint(g)
        floorButtonA.paint(g)
        floorButtonB.paint(g)
        g.color = Color.BLACK
        g.fillRect(0, floorButtonA.collider.bottom, Global.SCREEN_X, Global.SCREEN_Y * 8 / 10)
        g.color = Color.WHITE
        g.font = font.deriveFont(1, 16.0F)
        g.drawString(
            "BATTLETIME - ${gameInfo.getPassedTime()}",
            Global.SCREEN_X / 20,
            floorButtonA.collider.bottom * 105 / 100
        )
        singleShotBorder.paint(g)
        shotGunBorder.paint(g)
        sniperBorder.paint(g)
        burstShotBorder.paint(g)
        mrlBorder.paint(g)
        chakuraBorder.paint(g)
        g.font = font.deriveFont(1, 72.0F)
        g.color = Color.red
        g.drawString("${gameInfo.killCount}", Global.SCREEN_X * 85 / 100, floorButtonA.collider.bottom * 105 / 100)
        g.font = font.deriveFont(3, 20.0F)
        g.drawString("KILL PTS", Global.SCREEN_X * 90 / 100, floorButtonA.collider.bottom * 110 / 100)
        g.font = font.deriveFont(3, 100.0F)
        g.color = Color.white.brighter()
        g.drawString(
            "${gameInfo.distance / 100}",
            Global.SCREEN_X * 15 / 100,
            floorButtonA.collider.bottom * 15 / 100
        )
        g.font = font.deriveFont(3, 32.0F)
        g.drawString("Miles", Global.SCREEN_X * 20 / 100, Global.SCREEN_Y * 18 / 100)
        g.font = font.deriveFont(1, 35.0F)
        g.color = Color.yellow.brighter()
        g.drawString("GOLD", Global.SCREEN_X * 5 / 100, floorButtonA.collider.bottom * 115 / 100)
        g.drawString(
            "${gameInfo.money}", Global.SCREEN_X * 25 / 100 - countStringDistance(gameInfo.money) * 25,
            floorButtonA.collider.bottom * 115 / 100
        )


        for (mapArr in mapProductArr) {
            if (mapArr[0].painter.left < Global.WINDOW_WIDTH) {
                for (map in mapArr) {
                    map.paint(g)
                }
            }
        }
        if (headShot) {
            g.drawImage(
                headshot,
                Global.SCREEN_X * 7 / 10,
                Global.SCREEN_Y * 1 / 10,
                Global.SCREEN_X / 3,
                Global.SCREEN_Y / 4,
                null
            )
        }
        scroll?.paint(g)

        ammo.forEach { a ->
//            a.shakeValue().let { c ->
//                if (c != null) {
//                    shakeX += c.x.roundToInt()
//                    shakeY += c.y.roundToInt()
//                }
//            }
            a.paint(g)
        }

        enemyAmmo.forEach { a -> a.paint(g) }
        if (henceTime) {
            henceState.forEach { a -> a.paint(g) }
            g.color = Color.white
            g.font = font.deriveFont(5, 100.0F)
            g.drawString(
                "${delayDisplay.countLimit / 60 - delayDisplay.count / 60}",
                Global.SCREEN_X / 2,
                Global.SCREEN_Y / 9
            )
        }
        enemyArr.forEach { a ->
            a.paint(g)
            if (a.weapons.size != 1 && a.operateWeapon == a.getWeapon(3)) {
                a.operateWeapon.paintLine(
                    a.collider.centerX, a.collider.centerY,
                    mainRole.collider.centerX, mainRole.collider.centerY, Color.gray.darker(), g
                )
            }
        }
        if (mainRole.operateWeapon == mainRole.getWeapon(3)) {
            mainRole.operateWeapon.paintLine(
                mainRole.collider.centerX,
                mainRole.collider.centerY,
                mousePoint.x,
                mousePoint.y,
                Color.RED.darker(),
                g
            )
        }
//        g.drawImage(
//            crossHair,
//            mousePoint.x - Global.MapObject,
//            mousePoint.y - Global.MapObject,
//            Global.MapObject * 2,
//            Global.MapObject * 2,
//            null
//        )
        g.font = font.deriveFont(1, 30.0F)
        g.color = Color.ORANGE.brighter()
        g.drawString("Point:", 20, Global.SCREEN_Y * 5 / 20)
        g.drawString(
            "${this.gameInfo.getTotalPoint()}",
            250 - countStringDistance(gameInfo.getTotalPoint()) * 20,
            Global.SCREEN_Y * 5 / 20
        )

        mainRole.paint(g)
        ammo.forEach { a ->
//            a.shakeValue().let { c ->
//                if (c != null) {
//                    shakeX += c.x.roundToInt()
//                    shakeY += c.y.roundToInt()
//                }
//            }
            a.paint(g)
        }
        bomb?.paint(g)
        if (hint) {
            g.font = font.deriveFont(3, 70.0F)
            g.color = Color(1F, 1F, 1F, 0.6F)
            g.drawString("How far can you get? ", Global.SCREEN_X / 8, Global.SCREEN_Y / 2)
        }
        if(gameInfo.level >= 2) {
            fog.paint(g)
        }
        pause.paint(g)

    }

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = { e ->
            if (e is GameKernel.Input.Event.KeyTyped) {
                var c = e.data.keyChar

                if (kimFlag) {
                    when (c) {
                        '!' -> {
                            enemyArr.add(Enemy(mousePoint.x, mousePoint.y, 6).apply {
                                changeCharactor(0, 0)
                            })
                        }
                        '@' -> {
                            enemyArr.add(Enemy(mousePoint.x, mousePoint.y, 6).apply {
                                changeCharactor(1, 0)
                            })
                        }
                        '#' -> {
                            enemyArr.add(Enemy(mousePoint.x, mousePoint.y, 6).apply {
                                changeCharactor(2, 1)
                            })
                        }
                        '$' -> {
                            enemyArr.add(Enemy(mousePoint.x, mousePoint.y, 6).apply {
                                changeCharactor(3, 2)
                            })
                        }
                        '%' -> {
                            enemyArr.add(Enemy(mousePoint.x, mousePoint.y, 6).apply {
                                changeCharactor(4, 3)
                            })
                        }
                        '^' -> {
                            enemyArr.add(Enemy(mousePoint.x, mousePoint.y, 6).apply {
                                changeCharactor(5, 4)
                            })
                        }
                        '&' -> {
                            enemyArr.add(Machine(mousePoint.x, mousePoint.y))
                        }

                        '*' -> {
                            if (scroll == null) {
                                scroll = Scroll()
                            }

                        }
                    }
                }


                gFStr += c
                var flag = false
                goldenFingerStringArray.forEach {
                    if (it.contains(gFStr)) {
                        flag = true
                    }
                    if (it == gFStr) {
                        when (gFStr) {
                            "Hank" -> {
                                hankFlag = !hankFlag
                                println("$gFStr mode is $hankFlag")
                            }
                            "KimCreate" -> {
                                kimFlag = !kimFlag
                                println("$gFStr mode is $kimFlag")
                            }
                            "Mario" -> {
                                mainRole.hamrioFlag = !mainRole.hamrioFlag

                            }
                        }
                        gFStr = ""
                    }
                }
                if (!flag) {
                    gFStr = "" + c
                }

            }

            run {
                if (!pause.click) {
                    mainRole.input?.invoke(e)
                }
                when (e) {
                    is GameKernel.Input.Event.MouseDragged -> {
                        mousePoint = Point(e.data.x, e.data.y)
                    }
                    is GameKernel.Input.Event.KeyKeepPressed -> {
                        if (e.data.keyCode == KeyEvent.VK_Q) {
                            SceneController.instance.change(GameOverScene())
                        }
                    }
                    is GameKernel.Input.Event.KeyPressed -> {
                        if (kimFlag) {
                            mainRole.ultBoolean = true
                        }
                        if (e.data.keyCode == KeyEvent.VK_SPACE && mainRole.ultBoolean) {
                            if (mainRole.dir == Global.Direction.RIGHT) {
                                bomb = Bomb(
                                    mainRole.collider.centerX,
                                    mainRole.collider.centerY,
                                    Global.SCREEN_X * 3 / 4,
                                    Global.SCREEN_Y * 1 / 3,
                                    mainRole.dir
                                )
                            } else if (mainRole.dir == Global.Direction.LEFT) {
                                bomb = Bomb(
                                    mainRole.collider.centerX,
                                    mainRole.collider.centerY,
                                    Global.SCREEN_X * 1 / 4,
                                    Global.SCREEN_Y * 1 / 3,
                                    mainRole.dir
                                )
                            }
                            AudioResourceController.getInstance()
                                .play(Path.Sounds.Bullets.ULT)
                            mainRole.ultLoading = 0
                        }
                    }
                    is GameKernel.Input.Event.MouseMoved -> {
                        mousePoint = Point(e.data.x, e.data.y)
                        if (!pause.click) {
                            var tmp: ArrayList<String> = ArrayList<String>(2)
                            tmp.add(e.data.x.toString())
                            tmp.add(e.data.y.toString())
                            henceState.forEach { a -> a.touch(e.data.x, e.data.y) }
                        }
                    }
                    is GameKernel.Input.Event.MousePressed -> {
                        mousePoint = Point(e.data.x, e.data.y)
                        if (pause.click(e.data.x, e.data.y)) {
                            pause.click = !pause.click
                        }
                        if (!pause.click) {
                            if (e.data.button == MouseEvent.BUTTON1 && !mainRole.isShield &&
                                !pause.touch(e.data.x, e.data.y) &&
                                (!henceTime || (!henceState[0].touch(e.data.x, e.data.y) &&
                                        !henceState[1].touch(e.data.x, e.data.y) &&
                                        !henceState[2].touch(e.data.x, e.data.y)))
                            ) {
                                if (e.data.y <= floorButtonA.collider.bottom) {
                                    shotFlag = true
                                    val tmp = Vector.xyToVector(
                                        Coordinate(
                                            (e.data.x - mainRole.painter.centerX).toDouble(),
                                            (e.data.y - mainRole.painter.centerY).toDouble()
                                        )
                                    )
                                    mainRole.shot(
                                        mainRole.painter.centerX,
                                        mainRole.painter.centerY,
                                        tmp.degree
                                    )?.forEach { a ->
                                        bulletGenSound(a)
                                        ammo.add(a)
                                        gameInfo.spendAmmo++
                                        if (mainRole.ultLoading < 100) {
                                            mainRole.ultLoading += 1
                                        }
                                    }
                                }
                            }
                            if (e.data.button == MouseEvent.BUTTON3) {
                                mainRole.reload()
                                if (singleShotBorder.click(
                                        e.data.x,
                                        e.data.y
                                    ) && singleShotBorder.purchasable(gameInfo.money)
                                ) {
                                    mainRole.getWeapon(0).ammoMagazine++
                                    gameInfo.money -= singleShotBorder.price
                                }
                                if (burstShotBorder.click(
                                        e.data.x,
                                        e.data.y
                                    ) && burstShotBorder.purchasable(gameInfo.money)
                                ) {
                                    mainRole.getWeapon(1).ammoMagazine++
                                    gameInfo.money -= burstShotBorder.price
                                }
                                if (shotGunBorder.click(
                                        e.data.x,
                                        e.data.y
                                    ) && shotGunBorder.purchasable(gameInfo.money)
                                ) {
                                    mainRole.getWeapon(2).ammoMagazine++
                                    gameInfo.money -= shotGunBorder.price
                                }
                                if (sniperBorder.click(
                                        e.data.x,
                                        e.data.y
                                    ) && sniperBorder.purchasable(gameInfo.money)
                                ) {
                                    mainRole.getWeapon(3).ammoMagazine++
                                    gameInfo.money -= sniperBorder.price
                                }
                                if (mrlBorder.click(e.data.x, e.data.y) && mrlBorder.purchasable(gameInfo.money)) {
                                    mainRole.getWeapon(4).ammoMagazine++
                                    gameInfo.money -= mrlBorder.price
                                }
                                if (chakuraBorder.click(
                                        e.data.x,
                                        e.data.y
                                    ) && chakuraBorder.purchasable(gameInfo.money)
                                ) {
                                    mainRole.getWeapon(5).ammoMagazine++
                                    gameInfo.money -= chakuraBorder.price
                                }
                            }
                            if (singleShotBorder.click(e.data.x, e.data.y)) {
                                mainRole.setWeapon(0)
                                singleShotBorder.state = true;
                                shotGunBorder.state = false;
                                burstShotBorder.state = false;
                                sniperBorder.state = false;
                                mrlBorder.state = false;
                                chakuraBorder.state = false;
                            }
                            if (burstShotBorder.click(e.data.x, e.data.y)) {
                                mainRole.setWeapon(1)
                                singleShotBorder.state = false;
                                shotGunBorder.state = false;
                                burstShotBorder.state = true;
                                sniperBorder.state = false;
                                mrlBorder.state = false;
                                chakuraBorder.state = false;
                            }
                            if (shotGunBorder.click(e.data.x, e.data.y)) {
                                mainRole.setWeapon(2)
                                singleShotBorder.state = false;
                                shotGunBorder.state = true;
                                burstShotBorder.state = false;
                                sniperBorder.state = false;
                                mrlBorder.state = false;
                                chakuraBorder.state = false;
                            }
                            if (sniperBorder.click(e.data.x, e.data.y)) {
                                mainRole.setWeapon(3)
                                singleShotBorder.state = false
                                shotGunBorder.state = false;
                                burstShotBorder.state = false;
                                sniperBorder.state = true;
                                mrlBorder.state = false;
                                chakuraBorder.state = false;
                            }
                            if (mrlBorder.click(e.data.x, e.data.y)) {
                                mainRole.setWeapon(4)
                                singleShotBorder.state = false;
                                shotGunBorder.state = false;
                                burstShotBorder.state = false;
                                sniperBorder.state = false;
                                mrlBorder.state = true;
                                chakuraBorder.state = false;
                            }
                            if (chakuraBorder.click(e.data.x, e.data.y)) {
                                mainRole.setWeapon(5)
                                singleShotBorder.state = false;
                                shotGunBorder.state = false;
                                burstShotBorder.state = false;
                                sniperBorder.state = false;
                                mrlBorder.state = false;
                                chakuraBorder.state = true;
                            }
                            if (henceState[0].click(e.data.x, e.data.y) && henceTime) {
                                mainRole.attack += 5
                                henceTime = false
                            }
                            if (henceState[1].click(e.data.x, e.data.y) && henceTime) {
                                mainRole.defense -= 5
                                henceTime = false
                            }
                            if (henceState[2].click(e.data.x, e.data.y) && henceTime) {
                                mainRole.hpMax += 50
                                henceTime = false
                            }
                        }
                    }
                    is GameKernel.Input.Event.MouseReleased -> {
                        shotFlag = false
                        mainRole.operateWeapon.releaseWeapon()
                    }
                    is GameKernel.Input.Event.KeyReleased -> {
//                        if (e.data.keyCode == KeyEvent.VK_SPACE && mainRole.bomb > 0) { //&& delayShot.count()
//                            if (mainRole.dir == Global.Direction.RIGHT) {
//
//                            } else if (mainRole.dir == Global.Direction.LEFT) {
//
//                            }
//                            mainRole.bomb--
//                        }
                    }
                }
            }
        }


    private fun collection(role: MainRole) {  //判定碰撞
        if (!role.isCollision(floorButtonA) || !role.isCollision(floorButtonB)) {
            var stairBoolean: Boolean = false
            mapProductArr.forEach { e ->
                e.forEach { a ->
                    if (a.isCollision(role)) {
                        if (a.type == StairObject.StairType.FLOOR) {
                            stairBoolean = true
                            return@forEach
                        }
                    }
                }
                if (stairBoolean) return@forEach
            }
            if (!stairBoolean && !role.skyMode) {
                role.translate(y = 20)
            }
        }
        if (role.isCollision(floorButtonA) || role.isCollision(floorButtonB)) {
            role.translate(y = -(role.collider.bottom - floorButtonA.collider.top))
        }

        mapProductArr.forEach { e ->
            e.forEach { a ->
                if (a.type == StairObject.StairType.FLOOR_UP) {
                    a.isCollisionUPStair(role)
                    return@forEach
                }
                if (a.type == StairObject.StairType.FLOOR_DOWN) {
                    a.isCollisionDOWNStair(role)
                    return@forEach
                }
            }
        }
    }

    private fun hitFloor(bulletComm: BulletComm, mapProductArr: MutableList<MutableList<StairObject>>): Boolean {
        var tmp: Boolean = false
        mapProductArr.forEach { it ->
            it.forEach {
                if (it.isCollision(bulletComm) && it.type == StairObject.StairType.FLOOR) {
                    tmp = true
                    return@forEach
                }
            }
            if (tmp) {
                return@forEach
            }
        }
        if (bulletComm.collider.bottom > (floorButtonA.collider.top) &&
            bulletComm.collider.bottom > (floorButtonB.collider.top)
        ) {
            tmp = true
        }
        return tmp
    }
}