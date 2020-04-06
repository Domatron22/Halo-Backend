//
//import org.jetbrains.exposed.sql.*
//import org.jetbrains.exposed.sql.transactions.transaction
//
///* Author: Dominic Triano
// * Date: 2/15/2020
// * Language: Kotlin
// * Project: Halo
// * Description:
// * This file will control the database functions
// *
// */
//
//
////Registers a hospital into the system
//fun registerCenter(MedId : String, grpCode : String)
//{
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver")//Starts Database Connection
//
//    transaction {
//        SchemaUtils.create(MedCenter)
//
//        //Creates a new Med center object with inputted values
//        MedCenter.insert {
//            it[hid] = MedId
//            it[groupId] = grpCode
//        }
//    }
//}
//
////registered the human into the system
//fun registerHuman(userId : String, pwrd : String, grpCode : String, lvl : Int)
//{
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") //Starts Database Connection
//
//    transaction {
//        SchemaUtils.create(Humans)
//
//        //Creates a new Human object using the inputted parameters
//        Humans.insert {
//            it[user] = userId
//            it[pass] = pwrd
//            it[groupId] = grpCode
//            it[uid] = userId.toInt()
//            it[fpath] = centerProbe(grpCode) + "/" + userId + grpCode
//            it[access] = lvl
//        }
//    }
//}
//
////checks to see if the Human exists
//fun humanProbe(userId : String, grpCode : String) : String
//{
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") //Starts Database connection
//
//    var usr : String = "N/A"
//
//    transaction {
//
//        val query: Query = Humans.slice(Humans.user, Humans.groupId)
//            .select { (Humans.user eq userId)and (Humans.user eq grpCode)}
//
//        usr = query.first()[Humans.user]
//    }
//
//    return usr // returns the confirmation that the user does or does not exist
//}
//
////Checks to see if the center exists
//fun centerProbe(grpCode: String) : String
//{
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") //Starts Database connection
//
//    var cntr : String = "N/A"
//
//    transaction {
//
//        val query: Query = MedCenter.select { (MedCenter.groupId eq grpCode)}
//
//        cntr = query.first()[MedCenter.groupId]
//    }
//
//    return cntr // returns the name of the medical center
//}
//
////returns the file path, to either save or retrieve a file
//fun fileManager(userId : String, grpCode : String) : String
//{
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") // Starts Database connection
//
//    var path : String = "N/A"
//
//    transaction {
//        //Find the filepath for the user
//        val query: Query = Humans.slice(Humans.user, Humans.groupId)
//            .select { (Humans.user eq userId) and (Humans.user eq grpCode)}
//
//        path = query.first()[Humans.fpath]
//    }
//
//    return path
//}
//
////gets a requested forum, returns the comments
//fun forumGet(forum : String) : Pair<MutableList<String>, MutableList<String>>
//{
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") // Starts database connection
//
//    var comments = Pair (mutableListOf(""), mutableListOf(""))
//
//    transaction {
//        var query: Query = Forums
//            .select{Forums.fname eq forum}
//
//        comments.first.add(query.first()[Forums.user])
//        comments.second.add(query.first()[Forums.comments])
//
//    }
//
//    return comments
//    //the variable comments is a pair of mutable lists
//    //that will be paired by user, and the comment that they made
//}
//
//fun logIn(usr: String, pass: String) : Boolean
//{
//    var flag = false
//
//    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") // Starts database connection
//
//    transaction {
//        var query: Query = Humans
//            .slice(Humans.user, Humans.pass)
//            .select{ (Humans.user eq usr) and (Humans.pass eq pass)}
//        if(query.first()[Humans.user] == usr && query.first()[Humans.pass] == pass )
//        {
//            flag = true
//        }
//    }
//
//    return flag
//}
//
////fun promoteHuman(usr : String, grpId : String, newLvl : Int)
////{
////    Database.connect("jdbc:h2:./test;DB_CLOSE_DELAY=-1", "org.h2.Driver") // Starts database connection
////
////    transaction {
////        Humans
////            .update(Humans.user = usr)
////            {
////                it[access] = newLvl
////            }
////
////    }
////}
//
