$(document).ready(function () {
    $('#processing').hide()
    $('#title h4 span').hide()

    var clicked = false
    $("#title").click(function () {
        if (clicked) {
            $("#chat").animate(
                { height: "50px" },
                500,
                function () {
                    $("#title h4 span").hide()
                }
            ).animate(
                { width: "50px" },
                500
            )

            clicked = !clicked
        } else {
            $("#chat").animate(
                { width: "400px"},
                500,
                function () {
                    $("#title h4 span").show()
                }
            ).animate(
                { height: "400px" },
                500
            )

            clicked = !clicked
        }
    })

    let icon = $("#message h3 i")
    let msg = $("#message h3 span")
    $("#chat_form form button").click(function () {
        disableInputs(true)

        $("#processing").show()
        setTimeout(
            function () {
                $("#processing").hide()

                if (!checkUsername() || !checkPassword()) {
                    icon.removeClass("fa fa-lock").removeClass("fa fa-check").addClass("fa fa-ban").css("color", "red")
                    msg.html("Invalid username or password").css("color", "red")
                } else {
                    icon.removeClass("fa fa-lock").removeClass("fa fa-ban").addClass("fa fa-check").css("color", "lightgreen")
                    msg.html("Authentication successful!").css("color", "lightgreen")
                }
    
                disableInputs(false)
            },
            3000
        )

        return false
    })
})

function disableInputs(cond) {
    $("#username").prop("disabled", cond)
    $("#password").prop("disabled", cond)
    $("#authenticate_button").prop("disabled", cond)
}

function checkUsername() {
    let username = $('#chat form #username').val()

    return username === "admin"
}

function checkPassword() {
    let password = $('#chat form #password').val()

    return password === "admin1234"
}