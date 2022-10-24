window.onload = function () {

    let contactForm = document.getElementsByClassName("contact-form")[0]
    contactForm.onsubmit = function () {
        return validateInputs()
    }

    document.getElementById("errors").style.display = "none"

    document.getElementsByName("reset")[0].addEventListener("click", function() {
        document.getElementById("errors").style.display = "none"

        document.getElementsByName("lastName")[0].classList.remove("invalid")
        document.getElementsByName("lastName")[0].classList.remove("valid")
        document.getElementsByName("firstName")[0].classList.remove("invalid")
        document.getElementsByName("firstName")[0].classList.remove("valid")
        document.getElementsByName("address")[0].classList.remove("invalid")
        document.getElementsByName("address")[0].classList.remove("valid")
        document.getElementsByName("birthDate")[0].classList.remove("invalid")
        document.getElementsByName("birthDate")[0].classList.remove("valid")
        document.getElementsByName("phone")[0].classList.remove("invalid")
        document.getElementsByName("phone")[0].classList.remove("valid")
        document.getElementsByName("email")[0].classList.remove("invalid")
        document.getElementsByName("email")[0].classList.remove("valid")
    })

    document.getElementsByName("lastName")[0].onblur = validateLastName
    document.getElementsByName("firstName")[0].onblur = validateFirstName
    document.getElementsByName("address")[0].onblur = validateAddress
    document.getElementsByName("birthDate")[0].onblur = validateBirthDate
    document.getElementsByName("phone")[0].onblur = validatePhone
    document.getElementsByName("email")[0].onblur = validateEmail
}


function validateInputs() {

    document.getElementById("errors").style.display = "inline"

    let refErrorDetails = document.getElementById("errorDetails")
    let refErrorList = document.getElementById("errorList")

    var errors = []

    if (!validateLastName())
        errors.push(document.getElementsByTagName("label")[0].innerHTML)
    if (!validateFirstName())
        errors.push(document.getElementsByTagName("label")[1].innerHTML)
    if (!validateAddress())
        errors.push(document.getElementsByTagName("label")[2].innerHTML)
    if (!validateBirthDate())
        errors.push(document.getElementsByTagName("label")[3].innerHTML)
    if (!validatePhone())
        errors.push(document.getElementsByTagName("label")[4].innerHTML)
    if (!validateEmail())
        errors.push(document.getElementsByTagName("label")[5].innerHTML)

    if (errors.length > 0) {
        refErrorDetails.innerHTML = "Invalid fields found!"
    } else {
        refErrorDetails.innerHTML = "Valid fields!"
    }

    errors.forEach(error => {
        let li = document.createElement("li")
        li.appendChild(document.createTextNode(error))
        refErrorList.appendChild(li)
    })

    return false
}


function validateLastName() {
    let refLastName = document.getElementsByName("lastName")[0]

    if (refLastName.value.length < 3) {
        refLastName.classList.remove("valid")
        refLastName.classList.add("invalid")

        return false
    } else {
        refLastName.classList.remove("invalid")
        refLastName.classList.add("valid")

        return true
    }
}

function validateFirstName() {
    let refFirstName = document.getElementsByName("firstName")[0]

    if (refFirstName.value.length < 3) {
        refFirstName.classList.remove("valid")
        refFirstName.classList.add("invalid")

        return false
    } else {
        refFirstName.classList.remove("invalid")
        refFirstName.classList.add("valid")

        return true
    }
}

function validateAddress() {
    let refAddress = document.getElementsByName("address")[0]
    let address = refAddress.value
    const numberReg = /\d+/
    const specialCharReg = /[@#$%^&*]/

    if (address.length < 3 || !numberReg.test(address) || specialCharReg.test(address)) {
        refAddress.classList.remove("valid")
        refAddress.classList.add("invalid")
        return false
    } else {
        refAddress.classList.remove("invalid")
        refAddress.classList.add("valid")
        return true
    }
}

function validateBirthDate() {
    let refBirthDate = document.getElementsByName("birthDate")[0]
    const reg = /^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/

    if (!reg.test(refBirthDate.value)) {
        refBirthDate.classList.remove("valid")
        refBirthDate.classList.add("invalid")
        return false
    } else {
        refBirthDate.classList.remove("invalid")
        refBirthDate.classList.add("valid")
        return true
    }
}

function validatePhone() {
    let refPhone = document.getElementsByName("phone")[0];
    const reg = /^(\d{3})\-(\d{9})$/;

    if (!reg.test(refPhone.value)) {
        refPhone.classList.remove("valid");
        refPhone.classList.add("invalid");
        return false
    } else {
        refPhone.classList.remove("invalid");
        refPhone.classList.add("valid");
        return true
    }
}

function validateEmail() {
    let refEmail = document.getElementsByName("email")[0]
    const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/

    if (!reg.test(refEmail.value)) {
        refEmail.classList.remove("valid");
        refEmail.classList.add("invalid");
        return false
    } else {
        refEmail.classList.remove("invalid");
        refEmail.classList.add("valid");
        return true
    }
}