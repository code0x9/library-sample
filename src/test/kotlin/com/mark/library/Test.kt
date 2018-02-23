package com.mark.library

import com.mark.library.ext.toDate
import org.openstack4j.model.common.Identifier
import org.openstack4j.openstack.OSFactory
import org.rythmengine.Rythm
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun main__(args: Array<String>) {
    val date = LocalDate.now()
    val result = date.format(
            DateTimeFormatter.ofPattern("MM월 DD일 E", Locale.KOREA))
    println(result)
}

fun main___(args: Array<String>) {
    val result = Rythm.render("""@args Date dueDate
<중앙2문헌실>
귀하(혹은 가족)의 도서반납일은 @dueDate.format("M월 d일(E)", Locale.KOREA)입니다.""",
            LocalDate.now().toDate())
    println(result)
}

// https://hub.docker.com/r/monasca/keystone/
fun main(args: Array<String>) {
    val os3 = OSFactory.builderV3()
            .endpoint("http://127.0.0.1:5000/v3")
            .credentials("admin", "s3cr3t", Identifier.byId("default"))
            .scopeToProject(Identifier.byName("library"), Identifier.byId("default"))
            .authenticate()

//    val member = os3.identity().users().getByName("member", "default")
//    println("--------- member : $member")
//    val guest = os3.identity().users().getByName("guest", "default")
//    println("--------- guest : $guest")
//
//    val project: Project = os3.identity().projects().getByName("library").first()
//    println("--------- project : $project")
//
//    val memberRole: Role = os3.identity().roles().getByName("member").first()
//    println("--------- memberRole : $memberRole")
//
//    val guestRole: Role = os3.identity().roles().getByName("guest").first()
//    println("--------- guestRole : $guestRole")
//
//    val result1 = os3.identity().roles().checkProjectUserRole(project.id, member.id, memberRole.id)
//    println("--------- result1 : $result1")
//    val result2 = os3.identity().roles().checkProjectUserRole(project.id, guest.id, guestRole.id)
//    println("--------- result2 : $result2")

    val res = OSFactory.builderV3()
            .endpoint("http://127.0.0.1:5000/v3")
            .token("1ad035c2c6384c9aa9f35708628c65eb")
            .scopeToProject(Identifier.byName("library"), Identifier.byId("default"))
            .authenticate()
    println("--------- res : $res")

    val member = res.identity().users().getByName("member", "default")
    println("--------- member : $member")
    val guest = res.identity().users().getByName("guest", "default")
    println("--------- guest : $guest")

}
