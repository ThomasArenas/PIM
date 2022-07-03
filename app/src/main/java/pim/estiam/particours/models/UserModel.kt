package pim.estiam.particours.models

class UserModel(
    val id: String = "User0",
    var last_name: String = "Nom de l'utilisateur",
    var first_name: String = "Prénom de l'utilisateur",
    var email: String = "utilisateur@mail.com",
    var password: String = "Mot de passe de l'utilisateur",
    var classe: String? = null,
    var spe: String? = null,
    var campus: String? = null
)