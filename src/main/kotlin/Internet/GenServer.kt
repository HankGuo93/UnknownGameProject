package Internet

import internet.server.Server

class GenServer {
    //多餘，應刪除此類，僅保留作為範例
    fun genServer():Server{
        val ss = Server(12345) //new出Server實體,於建構時給埠號(PORT)，再以start()方法啟動
        ss.start()
        return ss
    }





}