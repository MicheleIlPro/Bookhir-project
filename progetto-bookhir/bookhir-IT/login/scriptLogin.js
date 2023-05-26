function show() {
  if (document.getElementById("log").classList.contains("hide")) {
    document.getElementById("log").classList.remove("hide");
    document.getElementById("log").classList.add("show");
    document.getElementById("reg").classList.remove("show");
    document.getElementById("reg").classList.add("hide");
  }
}

function showR() {
  if (document.getElementById("reg").classList.contains("hide")) {
    document.getElementById("reg").classList.remove("hide");
    document.getElementById("reg").classList.add("show");
    document.getElementById("log").classList.remove("show");
    document.getElementById("log").classList.add("hide");
  }
}
