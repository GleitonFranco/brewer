package com.algaworks.brewer.repository.helper.cerveja;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CervejasImpl implements CervejasQueries {
    @PersistenceContext
    private EntityManager manager;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {
        Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cerveja.class);
        int registrosPorPagina = pageable.getPageSize();
        int paginaAtual = pageable.getPageNumber();
        int primeiroRegistro = paginaAtual * registrosPorPagina;

        criteria.setMaxResults(registrosPorPagina);
        criteria.setFirstResult(primeiroRegistro);
        if (filtro != null) {
            this.adicionarFiltro(criteria, filtro);
        }
        Sort sort = pageable.getSort();
        if (sort != null) {
            Sort.Order order = sort.iterator().next();
            String field = order.getProperty();
            criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field));
        }
        final List<Cerveja> lista = criteria.list();
        return new PageImpl<Cerveja>(lista, pageable, this.totalPaginas());
    }

    private void adicionarFiltro(Criteria criteria, CervejaFilter filtro) {
        if (!StringUtils.isEmpty(filtro.getSku())) {
            criteria.add(Restrictions.eq("sku", filtro.getSku()));
        }
        if (!StringUtils.isEmpty(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isEmpty(filtro.getOrigem())) {
            criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
        }
        if (!StringUtils.isEmpty(filtro.getSabor())) {
            criteria.add(Restrictions.eq("sabor", filtro.getSabor()));
        }
        if (!StringUtils.isEmpty(filtro.getValorDe())) {
            criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
        }
        if (!StringUtils.isEmpty(filtro.getValorAte())) {
            criteria.add(Restrictions.le("valor", filtro.getValorAte()));
        }
        if (this.isEstiloPresente(filtro)) {
            criteria.add(Restrictions.eq("estilo", filtro.getEstilo()));
        }
    }

    private Long totalPaginas() {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    private boolean isEstiloPresente(CervejaFilter filtro) {
        return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
    }
}
