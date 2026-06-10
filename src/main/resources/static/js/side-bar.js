async function refreshSidebar() {
    try {
        const res = await fetch('/schoolstock/s/me/students/info');
        if (!res.ok) return;
        const info = await res.json();
        const el = document.getElementById('sidebarPoint');
        if (el && info) el.textContent = info.totalPoint.toLocaleString();
    } catch (e) {}
}