document.addEventListener('DOMContentLoaded', () => {

    const couponCard = document.getElementById('coupon-card');
    if(couponCard){
        couponCard.addEventListener('click', () => {
            location.href = couponCard.dataset.href;
        });
    }

    document.getElementById('refresh-btn').addEventListener('click', refreshAssets);
});


async function refreshAssets() {
    try {
        const res = await fetch('/schoolstock/s/me/student/assets/stocks');
        if (!res.ok) throw new Error('status ' + res.status);
        const data = await res.json();

        setText('total-assets', comma(data.totalValue) + ' P');
        setText('total-points', comma(data.myPoint) + ' P');
        setText('coupon-count', data.couponAmount + '개');
        paintProfit(document.getElementById('total-profit'), data.totalProfit, ' P');

        const tbody = document.getElementById('stock-list');
        tbody.innerHTML = '';
        data.myStocks.forEach(s => {
            const tr = document.createElement('tr');
            tr.className = 'stock-row-item';
            tr.appendChild(td(s.stockName));
            tr.appendChild(td(s.stockAmount + '개'));
            tr.appendChild(td(comma(s.nowPoint) + 'P', 'stock-price'));
            tr.appendChild(td(comma(s.averagePoint) + 'P'));
            tr.appendChild(td(comma(s.purchasePoint) + 'P'));
            const profitTd = td('');
            paintProfit(profitTd, s.stockProfit, 'P');
            tr.appendChild(profitTd);
            tbody.appendChild(tr);
        });
    } catch (err) {
        alert('새로고침 실패: ' + err.message);
    }
}

function comma(n) { return n.toLocaleString(); }

function setText(id, text) { document.getElementById(id).textContent = text; }

function td(text, cls) {
    const el = document.createElement('td');
    el.textContent = text;
    if (cls) el.className = cls;     // 클래스 필요할 때만(예: stock-price)
    return el;
}

function paintProfit(el, value, suffix) {
    el.textContent = (value > 0 ? '+' : '') + comma(value) + suffix;
    el.classList.remove('plus', 'minus');   // 이전 상태 지우고
    el.classList.add(value >= 0 ? 'plus' : 'minus');
}