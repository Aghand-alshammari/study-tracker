(function () {
  "use strict";

  const menuButton = document.querySelector(".menu-toggle");
  const navigation = document.querySelector(".site-nav");
  const navigationLinks = navigation ? navigation.querySelectorAll("a[href^='#']") : [];

  function closeMenu() {
    if (!menuButton || !navigation) return;
    menuButton.setAttribute("aria-expanded", "false");
    menuButton.setAttribute("aria-label", "فتح قائمة التنقل");
    navigation.classList.remove("is-open");
  }

  if (menuButton && navigation) {
    menuButton.addEventListener("click", function () {
      const isOpen = menuButton.getAttribute("aria-expanded") === "true";
      menuButton.setAttribute("aria-expanded", String(!isOpen));
      menuButton.setAttribute("aria-label", isOpen ? "فتح قائمة التنقل" : "إغلاق قائمة التنقل");
      navigation.classList.toggle("is-open", !isOpen);
    });

    navigationLinks.forEach(function (link) {
      link.addEventListener("click", closeMenu);
    });

    document.addEventListener("keydown", function (event) {
      if (event.key === "Escape" && menuButton.getAttribute("aria-expanded") === "true") {
        closeMenu();
        menuButton.focus();
      }
    });

    document.addEventListener("click", function (event) {
      if (
        menuButton.getAttribute("aria-expanded") === "true" &&
        !navigation.contains(event.target) &&
        !menuButton.contains(event.target)
      ) {
        closeMenu();
      }
    });
  }

  const faqButtons = Array.from(document.querySelectorAll(".faq-item button"));

  function setFaqState(button, expanded) {
    const answer = document.getElementById(button.getAttribute("aria-controls"));
    button.setAttribute("aria-expanded", String(expanded));
    if (answer) answer.hidden = !expanded;
  }

  faqButtons.forEach(function (button, index) {
    setFaqState(button, index === 0);

    button.addEventListener("click", function () {
      const shouldOpen = button.getAttribute("aria-expanded") !== "true";
      faqButtons.forEach(function (otherButton) {
        setFaqState(otherButton, false);
      });
      if (shouldOpen) setFaqState(button, true);
    });

    button.addEventListener("keydown", function (event) {
      if (!["ArrowDown", "ArrowUp", "Home", "End"].includes(event.key)) return;
      event.preventDefault();

      let targetIndex = index;
      if (event.key === "ArrowDown") targetIndex = (index + 1) % faqButtons.length;
      if (event.key === "ArrowUp") targetIndex = (index - 1 + faqButtons.length) % faqButtons.length;
      if (event.key === "Home") targetIndex = 0;
      if (event.key === "End") targetIndex = faqButtons.length - 1;
      faqButtons[targetIndex].focus();
    });
  });
})();
