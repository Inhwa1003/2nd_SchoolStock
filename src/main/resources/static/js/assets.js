document.addEventListener('DOMContentLoaded', () => {

    const couponCard = document.getElementById('coupon-card');
    if(couponCard){
        couponCard.addEventListener('click', () => {
            location.href = couponCard.dataset.href;
        });
    }
    const grantBtn = document.getElementById('grant-btn');
    if (grantBtn) grantBtn.addEventListener('click', grantPoints);

    document.getElementById('refresh-btn').addEventListener('click', refreshAssets);
});


async function refreshAssets() {
    try {
        const url = document.getElementById('refresh-btn').dataset.url
            || '/schoolstock/s/me/students/assets/stocks';
        const res = await fetch(url);
        if (!res.ok) throw new Error('status ' + res.status);
        const data = await res.json();

        setText('total-assets', comma(data.totalValue) + ' P');
        setText('total-points', comma(data.myPoint) + ' P');
        setText('coupon-count', data.couponAmount + '개');
        paintProfit(document.getElementById('total-profit'), data.totalProfit, ' P');

        const tbody = document.getElementById('stock-list');
        tbody.innerHTML = '';
        if (!data.myStocks || data.myStocks.length === 0) {
            const tr = document.createElement('tr');
            tr.className = 'empty-stock-row';
            const cell = document.createElement('td');
            cell.colSpan = 6;

            cell.innerHTML = `
                <div class="empty-stock-state">
                <div class="empty-stock-icon">📦</div>
                <p>보유한 주식이 없습니다.</p>
                <span>주식 목록에서 원하는 주식을 확인해보세요.</span>
                </div>
            `;

            tr.appendChild(cell);
            tbody.appendChild(tr);
        } else {
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
        }
    } catch (err) {
        alert('새로고침 실패: ' + err.message);
    }
}

async function grantPoints() {
    const block = document.querySelector('.point-grant');
    const studentNumber = block.dataset.studentNumber;
    const points = Number(document.getElementById('grant-points').value);
    const content = document.getElementById('grant-content').value.trim() || '지급';
    if (!points || points <= 0) { alert('지급할 포인트를 입력하세요.'); return; }

    const headers = { 'Content-Type': 'application/json' };
    const token  = document.querySelector("meta[name='_csrf']")?.getAttribute('content');
    const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute('content');
    if (token && header) headers[header] = token;

    try {
        const res = await fetch(`/schoolstock/t/me/teachers/my-students/${studentNumber}/points`, {
            method: 'POST', headers: headers,
            body: JSON.stringify({ points: points, content: content })
        });
        if (!res.ok) throw new Error('status ' + res.status);
        alert('포인트를 지급했습니다.');
        refreshAssets();   // 보유포인트·총자산 즉시 갱신
    } catch (e) {
        alert('포인트 지급 실패: ' + e.message);
    }
}

function comma(n) { return n.toLocaleString(); }

function setText(id, text) { document.getElementById(id).textContent = text; }

function td(text, cls) {
    const el = document.createElement('td');
    el.textContent = text;
    if (cls) el.className = cls;
    return el;
}

function paintProfit(el, value, suffix) {
    el.textContent = (value > 0 ? '+' : '') + comma(value) + suffix;
    el.classList.remove('plus', 'minus');   // 이전 상태 지우고
    el.classList.add(value >= 0 ? 'plus' : 'minus');
}