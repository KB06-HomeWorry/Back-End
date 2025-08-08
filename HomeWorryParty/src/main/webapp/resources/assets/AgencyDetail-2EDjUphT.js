import{_ as H,d as u,h as P,x as V,i as d,c as g,e as p,a as e,q as x,S as q,t as c,F as w,y as S,g as O,o as r,n as j,f as M,D as $,j as z}from"./index-xNotGCl0.js";import{B as A,A as E,H as U,R as G}from"./ReviewBox-BFzYhJ-s.js";import{b as J,a as K}from"./star_outline-Cw4kId92.js";import{p as Q,a as W,b as X}from"./sample_profile3-DzvHCZTU.js";const Y=["title"],Z=["src"],ee={class:"agency-profile-wrap"},ae={class:"profile-row"},te=["src"],se={class:"profile-info"},oe={class:"agency-name titleBold16px"},ne={class:"hashtags"},ie={class:"agency-info-table"},ce={class:"bodyLight12px"},re={class:"bodyLight12px"},le={class:"bodyLight12px"},de={class:"bodyLight12px"},ue={class:"agency-description"},ge={class:"desc-content bodyLight12px"},pe={class:"agency-btn-row"},fe={key:0,class:"review-list-wrap"},me={key:1,class:"bodyLight12px",style:{"text-align":"center",color:"var(--color-mediumgray)",margin:"24px 0"}},_e={__name:"AgencyDetail",setup(he){const v=[Q,W,X],B=u(v[Math.floor(Math.random()*v.length)]),y=O(),b=P(),n=b.query.agencyId||b.params.agencyId||"1",f=localStorage.getItem("user-token"),a=u({office_name:"",profileUrl:"",hashtags:[],agent_name:"",license_number:"",address:"",phone:"",description:""}),m=u({finalTrustScore:"",averageMetricScores:{listing_accuracy_score:"",cost_transparency_score:"",professionalism_score:"",accountability_score:""}}),_=u([]),i=u(!1);async function I(){try{const s=await d.get(`/api/agent/${f}/isFavorite/${n}`);i.value=s.data}catch{i.value=!1}}async function L(){try{i.value?(await d.delete(`/api/agent/${f}/favorite/${n}`),i.value=!1):(await d.get(`/api/agent/${f}/favorite/${n}`),i.value=!0)}catch{alert("북마크 처리 중 오류가 발생했습니다.")}}V(async()=>{try{const s=await d.get(`http://54.66.153.95:8080/api/agent/${n}`);if(a.value={...a.value,office_name:s.data.officeName,agent_name:s.data.agentName,license_number:s.data.licenseNumber,address:s.data.address,phone:s.data.phone,description:s.data.description,hastags:s.data.hashtags||[]},!a.value.description||a.value.description.trim()===""){const l=[`🏢 안녕하세요, 젊은 감각을 가진 공인중개사입니다!

    저희는 최신 트렌드와 다양한 주거 니즈를 반영하여, 고객님의 라이프스타일에 꼭 맞는 공간을 제안해드립니다. 정직함과 신뢰를 바탕으로 항상 고객 중심의 중개를 약속합니다.

    ✔ 합리적인 가격, 빠른 매물 업데이트, 세심한 매물 관리를 최우선으로 합니다.
    ✔ 친근한 상담과 신속한 계약으로 믿고 맡기실 수 있습니다.

    ✨ 언제든 문의주시면 성심껏 도와드리겠습니다.
    여러분의 새로운 시작을 응원합니다! 😊`,`👩‍👧 안녕하세요, 여러분을 위해 발로 뛰는 중개사가 대기하고 있습니다!

    한 분 한 분의 소중한 집을 찾아드리는 것을 최우선으로 생각합니다. 처음부터 끝까지 꼼꼼하게 챙기며, 따뜻한 관심으로 정직한 거래를 실현합니다.

    ✔ 부담 없이 상담부터 시작하세요! 
    ✔ 믿을 수 있는 부동산 거래, 저희 부동산이 책임집니다.

    ✨ 언제든 문의주시면 최선을 다하겠습니다.
    고객님의 행복한 새 출발을 응원합니다! 😊`,`⏱ 안녕하세요, 시간은 금이다!

    빠르고 정확한 매물 소개로 고객님의 소중한 시간을 아껴드립니다. 투명한 정보 제공과 정직한 중개로 언제나 믿고 맡기실 수 있습니다.

    ✔ 신속한 매물 안내, 꼼꼼한 계약 진행
    ✔ 믿음직한 서비스로 스트레스 없는 거래를 약속드립니다.

    ✨ 언제든 문의주세요!
    고객님의 만족을 위해 항상 최선을 다하겠습니다. 😊`,`🤝 공감과 신뢰를 바탕으로 하는 정직한 중개사무소입니다!

    고객 한 분 한 분의 상황에 공감하며, 가장 이득이 되는 거래를 위해 늘 고민합니다. 신뢰와 약속을 최우선 가치로 삼아, 진심을 담아 상담해드립니다.

    ✔ 꼼꼼한 매물 안내와 고객 맞춤형 중개 서비스
    ✔ 정직하고 투명한 거래를 약속합니다.

    ✨ 언제든 편하게 연락 주세요.
    여러분의 든든한 부동산 파트너가 되겠습니다! 😊`,`🔎 광진구 제일의 부동산입니다!

    고객님이 원하는 집을 찾을 때까지 함께 고민하고 끝까지 책임집니다. 합리적인 가격, 세심한 매물 관리, 친절한 상담으로 신뢰받는 부동산이 되겠습니다.

    ✔ 다양한 매물 라인업, 꼼꼼한 비교 안내
    ✔ 친근하고 성실한 상담 서비스 제공

    ✨ 언제든 문의주시면 최선을 다하겠습니다.
    여러분의 행복한 내 집 마련을 응원합니다! 😊`,`🏡 내 집을 구하는 마음으로 정성을 다하는 공인중개사입니다!

    고객 한 분 한 분의 입장에서 최적의 매물을 추천드리며, 내 가족이 머물 집처럼 신중하게 중개합니다. 진심을 담은 서비스로 최고의 만족을 드리겠습니다.

    ✔ 맞춤형 매물 추천, 투명한 정보 제공
    ✔ 꼼꼼하고 믿을 수 있는 계약 진행

    ✨ 언제든 편하게 문의주세요!
    고객님의 새로운 시작을 진심으로 응원합니다. 😊`,`🎓 부동산학 박사 임원진이 함께하는 전문 중개법인입니다!

    경매·공매 분야에 특화된 전문가들이 실전 경험과 전문 지식으로 고객님께 맞춤 솔루션을 제공합니다. 복잡한 거래도 쉽게 풀어드릴 수 있도록 언제나 최선을 다합니다.

    ✔ 전문적이고 신뢰할 수 있는 매물 안내
    ✔ 상세하고 꼼꼼한 계약 진행 지원

    ✨ 언제든 문의주시면 성심껏 도와드리겠습니다.
    여러분의 성공적인 거래를 응원합니다! 😊`,`📍 24시간 준비되어있는 중개사무소 입니다!

    지역 전문가로서 평일·공휴일 언제든 방문하실 수 있고, 일요일은 휴무입니다. 현장 방문과 꼼꼼한 상담으로 고객님께 꼭 맞는 집을 찾을 수 있도록 함께합니다.

    ✔ 지역별 실시간 매물 안내, 맞춤형 추천
    ✔ 친절하고 신속한 상담 서비스

    ✨ 언제든 편하게 연락 주세요!
    여러분의 든든한 동반자가 되어드리겠습니다. 😊`,`📞 신의와 성실로 상담해드리는 공인중개사입니다!

    고객님의 입장에서 생각하며, 처음부터 끝까지 친절하게 안내합니다. 부담 없이 전화 주시면 항상 최선을 다해 상담해드리겠습니다.

    ✔ 맞춤형 상담, 꼼꼼한 계약 진행
    ✔ 믿을 수 있는 부동산 거래를 약속드립니다.

    ✨ 언제든 문의주세요!
    여러분의 만족을 위해 최선을 다하겠습니다. 😊`],h=Math.floor(Math.random()*l.length);a.value.description=l[h]}if(!a.value.hashtags||a.value.hashtags.length===0){let h=function(N,k=3,C=4){const D=[...N].sort(()=>.5-Math.random()),F=Math.floor(Math.random()*(C-k+1))+k;return D.slice(0,F)};const l=["#오피스텔","#오피스","#전월세","#월세","#전세","#원룸","#투룸","#상가","#빌라","#아파트","#신축","#분양","#매매","#임대","#신뢰감","#친절함","#발품중개","#현장방문","#정직중개","#역세권"];a.value.hashtags=h(l)}const t=await d.get(`http://54.66.153.95:8080/api/agent/trustScore/${n}`);m.value={...m.value,finalTrustScore:t.data.totalTrustScore,averageMetricScores:{listing_accuracy_score:t.data.listingAccuracyScore,cost_transparency_score:t.data.costTransparencyScore,professionalism_score:t.data.professionalismScore,accountability_score:t.data.accountabilityScore}};const o=await d.get(`http://54.66.153.95:8080/api/agent/reviews/${n}`);_.value=o.data,await I()}catch{alert("중개사무소 정보를 불러오지 못했습니다.")}});const T=()=>{y.push(`/agency/${n}/listings`)},R=()=>{y.push(`/agency/${n}/review-write`)};return(s,t)=>(r(),g("div",null,[p(q,{title:a.value.office_name},{action:x(()=>[e("button",{class:"bookmark-btn",onClick:L,"aria-label":"북마크",title:i.value?"북마크 해제":"북마크 등록"},[e("img",{src:i.value?M(J):M(K),class:j(["bookmark-icon",{pop:s.heartAnim}]),onAnimationend:t[0]||(t[0]=o=>s.heartAnim=!1),alt:"북마크"},null,42,Z)],8,Y)]),_:1},8,["title"]),e("div",ee,[e("div",ae,[e("img",{src:B.value,alt:"프로필",class:"profile-img"},null,8,te),e("div",se,[e("div",oe,c(a.value.office_name),1),e("div",ne,[(r(!0),g(w,null,S(a.value.hashtags,o=>(r(),$(U,{key:o},{default:x(()=>[z(c(o),1)]),_:2},1024))),128))])])]),e("table",ie,[e("tbody",null,[e("tr",null,[t[1]||(t[1]=e("td",{class:"bodyMedium12px"},"대표",-1)),e("td",ce,c(a.value.agent_name),1)]),e("tr",null,[t[2]||(t[2]=e("td",{class:"bodyMedium12px"},"등록번호",-1)),e("td",re,c(a.value.license_number),1)]),e("tr",null,[t[3]||(t[3]=e("td",{class:"bodyMedium12px"},"주소",-1)),e("td",le,c(a.value.address),1)]),e("tr",null,[t[4]||(t[4]=e("td",{class:"bodyMedium12px"},"연락처",-1)),e("td",de,c(a.value.phone),1)])])]),e("div",ue,[e("div",ge,c(a.value.description||"등록된 소개글이 없습니다."),1)]),e("div",pe,[p(A,{text:"보유 매물 보러가기",color:"#fff",onClick:T}),p(A,{text:"방문 후기 작성하기",color:"#fff",onClick:R})]),p(E,{"score-data":m.value},null,8,["score-data"]),_.value.length>0?(r(),g("div",fe,[(r(!0),g(w,null,S(_.value,(o,l)=>(r(),$(G,{key:o.id,index:l+1,date:o.createdAt,content:o.comment},null,8,["index","date","content"]))),128))])):(r(),g("div",me," 아직 작성된 후기가 없습니다. "))])]))}},xe=H(_e,[["__scopeId","data-v-32df2c24"]]);export{xe as default};
