package com.adaint.daftar_teman

class Data_teman{
    var alamat : String? = null
    var nama : String? = null
    var no_hp : String? = null
    var id : String? = null
    var key: String? = null
    var kondisi: String? = null

    constructor( alamat: String?,nama : String?, no_hp: String?){


        this.alamat = alamat
        this.nama = nama
        this.no_hp = no_hp
    }
    constructor() : this("", "","") {

    }

    constructor(key: String?) {
        this.key = key
    }

}