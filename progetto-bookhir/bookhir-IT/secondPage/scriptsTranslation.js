// Traslate code 
function translateToEnglish() {
    // Navbar
    document.querySelector("a.navbar-brand").textContent = "Bookhir";
    
    document.querySelector("li.nav-item.p-1 a.nav-link").nextSibling.textContent = "";
    document.querySelector("a.nav-link[href='/progetto-bookhir/bookhir-IT/secondPage/about.html']").textContent = "About";
    document.querySelector("a.nav-link[href='/progetto-bookhir/bookhir-IT/secondPage/logout.html']").textContent = "Logout";
    // Page Heading
    document.querySelector("h1.centered").textContent = "Welcome to our library";
    // Books Heading
    document.querySelector("h2").textContent = "All Books";
    // Footer
    
    document.querySelector(".text-uppercase.fw-bold.mb-4").textContent = "Latest Books";
    document.querySelector("a[href='#!'].text-reset").textContent = "Angular";
    document.querySelectorAll("a[href='#!'].text-reset")[1].textContent = "React";
    document.querySelectorAll("a[href='#!'].text-reset")[2].textContent = "Vue";
    document.querySelectorAll("a[href='#!'].text-reset")[3].textContent = "Laravel";
    document.querySelector(".text-uppercase.fw-bold.mb-4").nextSibling.textContent = "Social media links";

    document.querySelector(".text-uppercase.fw-bold.mb-4").nextSibling.nextSibling.textContent = "Contact";

    document.getElementById("english").classList.remove("show");
    document.getElementById("english").classList.add("hide");
    document.getElementById("ita").classList.remove("hide");
    document.getElementById("ita").classList.add("show");
  }

  function translateToItalian() {
    // Navbar
    document.querySelector("a.navbar-brand").textContent = "Bookhir";
    
    document.querySelector("li.nav-item.p-1 a.nav-link").nextSibling.textContent = "";
    document.querySelector("a.nav-link[href='/progetto-bookhir/bookhir-IT/secondPage/about.html']").textContent = "Informazioni";
    document.querySelector("a.nav-link[href='/progetto-bookhir/bookhir-IT/secondPage/logout.html']").textContent = "Logout";
    // Page Heading
    document.querySelector("h1.centered").textContent = "Benvenuti nella nostra biblioteca";
    // Books Heading
    document.querySelector("h2").textContent = "Tutti i libri";
   
    document.querySelector(".text-uppercase.fw-bold.mb-4").textContent = "Ultimi libri";
    document.querySelector("a[href='#!'].text-reset").textContent = "Angular";
    document.querySelectorAll("a[href='#!'].text-reset")[1].textContent = "React";
    document.querySelectorAll("a[href='#!'].text-reset")[2].textContent = "Vue";
    document.querySelectorAll("a[href='#!'].text-reset")[3].textContent = "Laravel";
    document.querySelector(".text-uppercase.fw-bold.mb-4").nextSibling.textContent = "Link social media";
    document.querySelector(".text-uppercase.fw-bold.mb-4").nextSibling.nextSibling.textContent = "Contatti";

    document.getElementById("ita").classList.remove("show");
    document.getElementById("ita").classList.add("hide");
    document.getElementById("english").classList.remove("hide");
    document.getElementById("english").classList.add("show");
    
  }