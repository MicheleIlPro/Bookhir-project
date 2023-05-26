function translateToEnglish() {
    document.querySelector("button h4").textContent = "Submit";
    const translations = {
      'Login': 'Login',
      'Iscriviti': 'Sign up',
      'Email': 'Email',
      'Password': 'Password',
      'La tua password': 'Your password',
      'Password dimenticata?': 'Forgot password?',
      'Nome': 'Name',
      'Cognome': 'Last Name',
      'Il tuo Cognome': 'Your Last Name',
      'La tua Password': 'Your password',
    };
  
    const elementsToTranslate = document.querySelectorAll('.button h4, label h4, input[placeholder], h6 a');
  
    elementsToTranslate.forEach(element => {
      const translationKey = element.textContent.trim();
      const translatedText = translations[translationKey];
      if (translatedText) {
        element.textContent = translatedText;
      }
    });
  }