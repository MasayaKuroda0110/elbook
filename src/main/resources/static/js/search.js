function performSearch() {
	const query = document.getElementById("search").value.trim();

	// クエリが空の場合、アラートを表示して中断
	if (!query) {
		alert("検索キーワードを入力してください！");
		return;
	}

	// チェックボックスの状態を確認
	const searchByTitle = document.getElementById("searchByTitle").checked;
	const searchByAuthor = document.getElementById("searchByAuthor").checked;

	// いずれの条件も選択されていない場合の対応
	if (!searchByTitle && !searchByAuthor) {
		alert("検索条件を選択してください！");
		return;
	}

	// パラメータの設定
	const params = new URLSearchParams();
	params.append("query", query);
	if (searchByTitle) params.append("searchBy", "title");
	if (searchByAuthor) params.append("searchBy", "author");

	// サーバーへのリクエスト
	fetch(`/books?${params.toString()}`, {
		method: 'GET'
	})
		.then(response => response.json())
		.then(data => updateTable(data))
		.catch(error => console.error("エラー発生:", error));
}

function allSearch() {
	resetSearchForm(); // まずフォームをリセット

	fetch(`/books?query=`, {
		method: 'GET'
	})
		.then(response => response.json())
		.then(data => updateTable(data))
		.catch(error => console.error("エラー発生:", error));
}

function resetSearchForm() {
	// 検索フィールドをクリア
	document.getElementById("search").value = "";

	// チェックボックスの状態をリセット
	document.getElementById("searchByTitle").checked = false;
	document.getElementById("searchByAuthor").checked = false;

	// サーバーからすべてのデータを取得
	fetch(`/books?query=`, {
		method: 'GET'
	})
		.then(response => response.json())
		.then(data => updateTable(data))
		.catch(error => {
			console.error("エラー発生:", error);
			const tableBody = document.getElementById("results");
			tableBody.innerHTML = `<tr><td colspan="5" style="color:red;">データ取得に失敗しました。</td></tr>`;
		});
}

function updateTable(data) {
	const tableBody = document.getElementById("results");
	tableBody.innerHTML = ""; // 現在のテーブル内容をクリア

	if (data.length === 0) {
		tableBody.innerHTML = `<tr><td colspan="5">データが存在しません。</td></tr>`;
		return;
	}

	data.forEach(book => {
		const authorName = book.author ? book.author.name : "著者不明";
		const transactionType = book.transaction?.transactionType || "不明";
		const row = `
            <tr>
                <td>${book.title}</td>
                <td>${authorName}</td>
                <td>${book.summary}</td>
                <td>${transactionType}</td>
                <td><a href="/editBook?id=${book.bookId}">編集</a></td>
            </tr>`;
		tableBody.innerHTML += row;
	});
}
