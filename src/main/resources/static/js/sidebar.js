document.addEventListener("DOMContentLoaded", () => {
  const container = document.getElementById("sidebar-container");

  if (!container) return;

  fetch("sidebar.html")
    .then((response) => response.text())
    .then((html) => {
      container.innerHTML = html;

      const riskMenu = document.getElementById("riskMenu");
      const riskToggle = document.getElementById("riskToggle");

      if (riskMenu && riskToggle) {
        riskToggle.addEventListener("click", () => {
          riskMenu.classList.toggle("open");
        });
      }

      setActiveMenu();
    })
    .catch((error) => {
      console.error("Sidebar load failed:", error);
    });
});

function setActiveMenu() {
  const path = window.location.pathname;
  const fileName = path.substring(path.lastIndexOf("/") + 1);

  const pageMap = {
    "index.html": "dashboard",
    "dashboard.html": "dashboard",

    "plan-inventory-input.html": "plan-input",
    "operation-input.html": "plan-input",

    "forecast-result.html": "risk-result",

    "item-detail.html": "item-detail",

    "mypage.html": "mypage"
  };

  const currentPage = pageMap[fileName];

  if (!currentPage) return;

  const activeLink = document.querySelector(`[data-page="${currentPage}"]`);

  if (activeLink) {
    activeLink.classList.add("active");
  }

  if (
    currentPage === "plan-input" ||
    currentPage === "risk-result" ||
    currentPage === "review-history"
  ) {
    const riskMenu = document.getElementById("riskMenu");
    if (riskMenu) {
      riskMenu.classList.add("open");
    }
  }
}